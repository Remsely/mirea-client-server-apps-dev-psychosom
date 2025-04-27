package ru.remsely.psyhosom.telegram.callback

import arrow.core.Either
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.value_object.TelegramChatId
import ru.remsely.psyhosom.monitoring.log.logger

abstract class BaseCallback(
    private val command: (Long, TelegramChatId) -> Either<DomainError, Unit>
) {
    protected val log = logger()

    fun execute(data: CallbackData) {
        val (absSender, chatId, rawData, queryId) = data

        log.info("Callback received in chat ${chatId.value} with data $rawData")

        val parts = rawData.split(" ")
        if (parts.size != 2) {
            answerError(absSender, queryId)
        }

        val consultationId: Long = parts[1].toLongOrNull()
            ?: answerError(absSender, queryId).let { return }

        command(consultationId, chatId)
            .fold(
                { err ->
                    answerError(absSender, queryId, err.message)
                },
                {
                    absSender.execute(
                        AnswerCallbackQuery().apply {
                            callbackQueryId = queryId
                        }
                    ).also {
                        log.info("Callback successfully executed in chat ${chatId.value} with data $rawData")
                    }
                }
            )
    }

    private fun answerError(absSender: AbsSender, queryId: String, message: String? = null) {
        absSender.execute(
            AnswerCallbackQuery().apply {
                callbackQueryId = queryId
                text = message ?: "Произошла ошибка"
                showAlert = false
            }
        ).also {
            log.warn("Error while executing callback. Message: $message")
        }
    }
}
