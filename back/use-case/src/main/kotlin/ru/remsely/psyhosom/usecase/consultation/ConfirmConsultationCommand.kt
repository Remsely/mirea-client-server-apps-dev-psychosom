package ru.remsely.psyhosom.usecase.consultation

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.value_object.TelegramChatId

interface ConfirmConsultationCommand {
    fun execute(consultationId: Long, chatId: TelegramChatId): Either<DomainError, Unit>
}

sealed class ConsultationConfirmationError(override val message: String) : DomainError.ValidationError {
    data class WrongChat(
        private val chatId: TelegramChatId,
        private val consultationId: Long
    ) : ConsultationConfirmationError(
        "Forbidden to consultation $consultationId from chat ${chatId.value}."
    )
}
