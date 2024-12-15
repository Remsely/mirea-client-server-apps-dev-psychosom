package ru.remsely.psyhosom.usecase.auth

import arrow.core.Either
import ru.remsely.psyhosom.domain.account.event.LoginAccountEvent
import ru.remsely.psyhosom.domain.account.event.RegisterAccountEvent
import ru.remsely.psyhosom.domain.error.DomainError

interface AuthService {
    fun registerUser(event: RegisterAccountEvent): Either<DomainError, String>

    fun loginUser(event: LoginAccountEvent): Either<DomainError, String>
}

sealed class UserLoginError(override val message: String) : DomainError.ValidationError {
    data class AuthenticationError(private val username: String) : UserLoginError(
        "Incorrect username or password."
    )
}

sealed class UserRegisterValidationError(override val message: String) : DomainError.ValidationError {
    data object InvalidUsername : UserRegisterValidationError(
        "User username must be telegram username or phone number."
    )
}
