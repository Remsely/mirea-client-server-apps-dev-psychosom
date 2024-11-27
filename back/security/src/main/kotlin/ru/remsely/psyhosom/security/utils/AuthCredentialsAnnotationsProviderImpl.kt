package ru.remsely.psyhosom.security.utils

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.usecase.auth.AuthCredentialsAnnotationsProvider

@Component
class AuthCredentialsAnnotationsProviderImpl : AuthCredentialsAnnotationsProvider {
    override fun getAuthUserId(): Long {
        return (SecurityContextHolder.getContext().authentication.principal as Jwt)
            .claims["id"]!!
            .toString()
            .toLong()
    }
}
