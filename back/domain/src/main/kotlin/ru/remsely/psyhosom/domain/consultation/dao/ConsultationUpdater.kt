package ru.remsely.psyhosom.domain.consultation.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.consultation.Consultation
import ru.remsely.psyhosom.domain.error.DomainError

interface ConsultationUpdater {
    fun cancelConsultation(consultation: Consultation): Either<DomainError, Consultation>

    fun rejectConsultation(consultation: Consultation): Either<DomainError, Consultation>

    fun confirmConsultation(consultation: Consultation): Either<DomainError, Consultation>

    fun finishConsultation(consultationId: Long): Boolean // TODO: убрать

    fun updateConsultations(consultations: List<Consultation>): Either<DomainError, Unit>
}

sealed class ConsultationUpdateError(override val message: String) : DomainError.ValidationError {
    data class WrongStatusToCancel(private val status: Consultation.Status) : ConsultationUpdateError(
        "Consultation with status $status cannot be canceled."
    )

    data class WrongStatusToReject(private val status: Consultation.Status) : ConsultationUpdateError(
        "Consultation with status $status cannot be rejected."
    )

    data class WrongStatusToConfirm(private val status: Consultation.Status) : ConsultationUpdateError(
        "Consultation with status $status cannot be confirmed."
    )
}
