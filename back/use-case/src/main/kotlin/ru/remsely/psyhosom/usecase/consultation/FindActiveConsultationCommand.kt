package ru.remsely.psyhosom.usecase.consultation

import arrow.core.Either
import ru.remsely.psyhosom.domain.consultation.Consultation
import ru.remsely.psyhosom.domain.consultation.event.FindActiveConsultationsEvent
import ru.remsely.psyhosom.domain.error.DomainError

interface FindActiveConsultationCommand {
    fun execute(event: FindActiveConsultationsEvent): Either<DomainError, Consultation>
}
