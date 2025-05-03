package ru.remsely.psyhosom.usecase.consultation

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.value_object.TelegramChatId

interface CancelConsultationCommand {
    fun execute(consultationId: Long, chatId: TelegramChatId): Either<DomainError, Unit>
}

sealed class ConsultationCancelError(override val message: String) : DomainError.ValidationError {
    data class WrongChat(
        private val chatId: TelegramChatId,
        private val consultationId: Long
    ) : ConsultationCancelError(
        "Forbidden to consultation $consultationId from chat ${chatId.value}."
    )
}
