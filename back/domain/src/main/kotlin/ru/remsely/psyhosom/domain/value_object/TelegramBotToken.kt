package ru.remsely.psyhosom.domain.value_object

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import ru.remsely.psyhosom.domain.error.DomainError
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val tokenPrefix = "tg"
private const val randomTokenPartLength = 10
private const val pattern = "^$tokenPrefix-[A-Za-z0-9]{${randomTokenPartLength}}-\\d{10}\$"
private const val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
private val identityDateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyHHmm")

@JvmInline
value class TelegramBotToken private constructor(val value: String) {
    companion object {
        operator fun invoke(value: String): Either<DomainError, TelegramBotToken> = either {
            ensure(value.matches(pattern.toRegex())) {
                TelegramBotTokenValidationError.InvalidTelegramBotToken(value)
            }
            TelegramBotToken(value)
        }

        fun generate() = TelegramBotToken(
            "$tokenPrefix-${generateRandomString()}-${identityDateTimeFormatter.format(LocalDateTime.now())}"
        )
    }
}

private fun generateRandomString() = (1..randomTokenPartLength)
    .map {
        chars.random()
    }
    .joinToString("")


sealed class TelegramBotTokenValidationError(override val message: String) : DomainError.ValidationError {
    data class InvalidTelegramBotToken(private val token: String) : TelegramBotTokenValidationError(
        "Telegram bot token $token is invalid."
    )
}
