package ru.remsely.psyhosom.domain.value_object

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import ru.remsely.psyhosom.domain.error.DomainError

@JvmInline
value class TelegramChatId private constructor(val value: Long?) {
    companion object {
        operator fun invoke(value: String): Either<DomainError.ValidationError, TelegramChatId> = Either.catch {
            value.toLong()
        }.fold(
            { TelegramChatIdValidationError.InvalidTelegramChatId.left() },
            { TelegramChatId(it).right() }
        )

        operator fun invoke(value: Long?): Either<DomainError.ValidationError, TelegramChatId> =
            TelegramChatId(value).right()
    }
}

sealed class TelegramChatIdValidationError(override val message: String) : DomainError.ValidationError {
    data object InvalidTelegramChatId : TelegramUsernameValidationError(
        "Telegram chat id is invalid."
    )
}
