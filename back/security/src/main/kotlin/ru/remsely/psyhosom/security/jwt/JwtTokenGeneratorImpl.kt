package ru.remsely.psyhosom.security.jwt

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.extentions.logger
import ru.remsely.psyhosom.security.user.User
import java.time.Instant

@Component
class JwtTokenGeneratorImpl(
    private val jwtEncoder: JwtEncoder
) : JwtTokenGenerator {
    override fun generate(auth: Authentication): String =
        JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(Instant.now())
            .subject(auth.name)
            .claim("role", auth.authorities.first())
            .claim("id", (auth.principal as User).id.toString())
            .build()
            .let { claims ->
                jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
            }.also {
                logger().info("Token was generated successfully.")
            }
}
