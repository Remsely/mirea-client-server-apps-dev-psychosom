package ru.remsely.psihosom.app.config.security

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
import ru.remsely.psihosom.security.service.UserDetailsServiceImpl
import ru.remsely.psihosom.security.jwt.RsaKeyProperties

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val rsaKeyProperties: RsaKeyProperties
) {
    @Value("\${frontend.url}")
    private lateinit var websiteUrl: String

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http.csrf { it.disable() }
            .formLogin { it.disable() }
            .cors { it.configurationSource(corsConfig()) }
            .authorizeHttpRequests { auth ->
                auth.apply {
                    requestMatchers("/v1/admin/**").hasRole("ADMIN")
                    requestMatchers("/v1/**").permitAll()
                    anyRequest().permitAll()
                }
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