package ru.remsely.psyhosom.usecase.consultation

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.consultation.dao.ConsultationFinder
import ru.remsely.psyhosom.domain.consultation.dao.ConsultationUpdater
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.value_object.TelegramChatId

@Component
open class CancelConsultationCommandImpl(
    private val consultationFinder: ConsultationFinder,
    private val consultationUpdater: ConsultationUpdater
) : CancelConsultationCommand {
    override fun execute(consultationId: Long, chatId: TelegramChatId): Either<DomainError, Unit> = either {
        val consultation = consultationFinder
            .findConsultationById(consultationId)
            .bind()

        ensure(chatId == consultation.patient.account.tgChatId) {
            ConsultationCancelError.WrongChat(chatId, consultationId)
        }

        consultationUpdater.cancelConsultation(consultation).bind()
    }
}
