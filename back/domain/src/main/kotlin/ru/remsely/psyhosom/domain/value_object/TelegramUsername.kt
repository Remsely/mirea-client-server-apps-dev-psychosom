package ru.remsely.psyhosom.domain.value_object

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import ru.remsely.psyhosom.domain.error.DomainError

private const val pattern = "^@[a-zA-Z0-9](?:[a-zA-Z0-9_]{3,30}[a-zA-Z0-9])?\$"

@JvmInline
value class TelegramUsername private constructor(val value: String?) {
    companion object {
        operator fun invoke(value: String?): Either<DomainError.ValidationError, TelegramUsername> = either {
            if (value == null) {
                return@either TelegramUsername(null)
            }
            ensure(value.matches(pattern.toRegex())) {
                TelegramUsernameCreationError.InvalidTelegramUsername
            }
            TelegramUsername(value)
        }
    }
}

sealed class TelegramUsernameCreationError(override val message: String) : DomainError.ValidationError {
    data object InvalidTelegramUsername : TelegramUsernameCreationError(
        "Telegram username is invalid."
    )
}
