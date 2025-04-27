package ru.remsely.psyhosom.telegram.callback

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.value_object.TelegramChatId

data class CallbackData private constructor(
    val absSender: AbsSender,
    val chatId: TelegramChatId,
    val rawData: String,
    val queryId: String
) {
    companion object {
        operator fun invoke(
            absSender: AbsSender,
            update: Update
        ): Either<DomainError, CallbackData> = either {
            ensure(update.hasCallbackQuery()) {
                CallbackDataValidationError.HasNoCallbackQuery
            }

            val callbackQuery = update.callbackQuery

            CallbackData(
                absSender = absSender,
                chatId = TelegramChatId(callbackQuery.message.chatId).bind(),
                rawData = callbackQuery.data,
                queryId = callbackQuery.id
            )
        }
    }

    sealed class CallbackDataValidationError(override val message: String) : DomainError.ValidationError {
        data object HasNoCallbackQuery : CallbackDataValidationError(
            "Update object has no callback query."
        )
    }
}