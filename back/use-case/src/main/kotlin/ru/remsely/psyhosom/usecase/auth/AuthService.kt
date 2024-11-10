package ru.remsely.psyhosom.usecase.auth

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.user.User
import ru.remsely.psyhosom.usecase.auth.request.AuthRequest

interface AuthService {
    fun registerUser(request: AuthRequest, role: User.Role): Either<DomainError, String>

    fun loginUser(request: AuthRequest): Either<DomainError, String>
}

sealed class UserLoginError(override val message: String) : DomainError.ValidationError {
    data class AuthenticationError(private val username: String) : UserLoginError(
        "Incorrect username or password."
    )
}
