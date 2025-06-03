package ru.remsely.psyhosom.security.jwt

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.security.user.User
import java.time.Instant

@Component
class JwtTokenGeneratorImpl(
    private val jwtEncoder: JwtEncoder
) : JwtTokenGenerator {
    private val log = logger()

    override fun generate(auth: Authentication): String =
        (auth.principal as User).let { user ->
            JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .subject(user.username)
                .claim("role", user.role.name)
                .claim("id", user.id.toString())
                .build()
                .let { claims ->
                    jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
                }.also {
                    log.info("Token was generated successfully.")
                }
        }
}
