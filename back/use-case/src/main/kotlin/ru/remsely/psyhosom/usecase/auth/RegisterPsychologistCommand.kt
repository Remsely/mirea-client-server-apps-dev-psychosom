package ru.remsely.psyhosom.usecase.auth

import arrow.core.Either
import ru.remsely.psyhosom.domain.account.event.RegisterPsychologistEvent
import ru.remsely.psyhosom.domain.error.DomainError

interface RegisterPsychologistCommand {
    fun execute(event: RegisterPsychologistEvent): Either<DomainError, AccountCreatedEvent>
}

sealed class PsychologistRegisterValidationError(override val message: String) : DomainError.ValidationError {
    data object InvalidUsername : PsychologistRegisterValidationError(
        "User username must be telegram username or phone number."
    )
}
