package ru.remsely.psyhosom.domain.value_object

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import ru.remsely.psyhosom.domain.error.DomainError

private const val pattern = "^\\+7\\d{10}\$"

@JvmInline
value class PhoneNumber private constructor(val value: String?) {
    companion object {
        operator fun invoke(value: String?): Either<DomainError.ValidationError, PhoneNumber> = either {
            if (value == null) {
                return@either PhoneNumber(null)
            }
            ensure(value.matches(pattern.toRegex())) {
                PhoneNumberValidationError.InvalidPhoneNumber
            }
            PhoneNumber(value)
        }
    }
}

sealed class PhoneNumberValidationError(override val message: String) : DomainError.ValidationError {
    data object InvalidPhoneNumber : PhoneNumberValidationError(
        "Phone number is invalid."
    )
}
