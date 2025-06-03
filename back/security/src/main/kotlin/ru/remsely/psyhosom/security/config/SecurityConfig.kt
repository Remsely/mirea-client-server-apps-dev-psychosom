package ru.remsely.psyhosom.security.config

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import ru.remsely.psyhosom.domain.account.Account
import ru.remsely.psyhosom.security.extensions.public
import ru.remsely.psyhosom.security.extensions.withRole
import ru.remsely.psyhosom.security.jwt.RsaKeyProperties
import ru.remsely.psyhosom.security.service.UserDetailsServiceImpl

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val rsaKeyProperties: RsaKeyProperties,

    @Value("\${frontend.url}")
    private val websiteUrl: String
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http.csrf { it.disable() }
            .formLogin { it.disable() }
            .cors { it.configurationSource(corsConfig()) }
            .authorizeHttpRequests { auth ->
                auth
                    .withRole(Account.Role.ADMIN) {
                        post("/api/v1/auth/register/admin")
                    }
                    .withRole(Account.Role.PATIENT) {
                        put("/api/v1/patients")
                        get("/api/v1/patients")
                        post("/api/v1/psychologists/*/consultations")
                        get("/api/v1/psychologists/*/consultations/active")
                        patch("/api/v1/psychologists/*/consultations/*/finish")
                        post("/api/v1/psychologists/*/reviews")
                    }
                    .withRole(Account.Role.PSYCHOLOGIST) {
                        put("/api/v1/psychologists/article")
                        post("/api/v1/psychologists/education")
                    }
                    .public {
                        // web socket
                        get("/ws/auth/confirmation")

                        // rest
                        post("/api/v1/auth/**")
                        get("/api/v1/psychologists/*/reviews")
                        get("/api/v1/psychologists/catalog")
                        get("/api/v1/psychologists/*")
                        get("/api/v1/psychologists/*/schedule")
                        any("/v3/api-docs/**")
                        any("/swagger-ui/**")
                        any("/swagger-ui.html")
                        any("/swagger-ui/index.html")
                        any("/swagger-resources/**")
                        any("/webjars/**")
                    }
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt {
                    it.jwtAuthenticationConverter(jwtAuthenticationConverter())
                }
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .build()

    @Bean
    fun corsConfig(): CorsConfigurationSource =
        CorsConfiguration().apply {
            allowedOrigins = listOf(websiteUrl)
            allowedMethods = listOf("*")
            allowedHeaders = listOf("*")
            exposedHeaders = listOf("*")
            allowCredentials = true
        }.let {
            UrlBasedCorsConfigurationSource().apply { registerCorsConfiguration("/**", it) }
        }

    @Bean
    fun authenticationManager(userDetailsService: UserDetailsServiceImpl): AuthenticationManager =
        DaoAuthenticationProvider().apply {
            setUserDetailsService(userDetailsService)
            setPasswordEncoder(passwordEncoder())
        }.let {
            ProviderManager(it)
        }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter =
        JwtAuthenticationConverter().apply {
            setJwtGrantedAuthoritiesConverter(
                JwtGrantedAuthoritiesConverter().apply {
                    setAuthoritiesClaimName("role")
                    setAuthorityPrefix("ROLE_")
                }
            )
        }

    @Bean
    fun jwtDecoder(): JwtDecoder = NimbusJwtDecoder.withPublicKey(rsaKeyProperties.publicKey).build()

    @Bean
    fun jwtEncoder(): JwtEncoder = RSAKey
        .Builder(rsaKeyProperties.publicKey)
        .privateKey(rsaKeyProperties.privateKey)
        .build()
        .let { ImmutableJWKSet<SecurityContext>(JWKSet(it)) }
        .let { NimbusJwtEncoder(it) }
}
