package ru.remsely.psyhosom.domain.consultation.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.consultation.Consultation
import ru.remsely.psyhosom.domain.error.DomainError

interface ConsultationUpdater {
    fun cancelConsultation(consultation: Consultation): Either<DomainError, Consultation>
}

sealed class ConsultationUpdateError(override val message: String) : DomainError.ValidationError {
    data class WrongStatusToCancel(private val status: Consultation.Status) : ConsultationUpdateError(
        "Consultation with status $status cannot be canceled."
    )
}
