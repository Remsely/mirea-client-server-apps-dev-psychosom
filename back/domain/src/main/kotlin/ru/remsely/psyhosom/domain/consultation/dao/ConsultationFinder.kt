package ru.remsely.psyhosom.domain.consultation.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.consultation.Consultation
import ru.remsely.psyhosom.domain.error.DomainError

interface ConsultationFinder {
    fun findConsultationById(consultationId: Long): Either<DomainError, Consultation>

    fun existActiveConsultationByPatientAndPsychologist(patientId: Long, psychologistId: Long): Boolean

    fun existFinishedConsultationByPatientAndPsychologist(patientId: Long, psychologistId: Long): Boolean

    fun findActiveSessionByPatientIdAndPsychologistId(
        patientId: Long,
        psychologistId: Long
    ): Either<DomainError, Consultation>

    fun findAllConfirmedConsultationsToNotify(): List<Consultation>

    fun findAllFinishedConsultationsToInform(): List<Consultation>
}

sealed class ConsultationFindingError(override val message: String) : DomainError.BusinessLogicError {
    data class MoreThanOneActiveFoundByPatientAndPsychologist(
        private val patientId: Long,
        private val psychologistId: Long
    ) : ConsultationFindingError(
        "More than one active session for patient $patientId and psychologist $psychologistId were found."
    )
}

sealed class ConsultationMissingError(override val message: String) : DomainError.MissingError {
    data class NotFoundById(private val id: Long) : ConsultationFindingError(
        "Consultation with id $id not found."
    )

    data class NotFoundActiveByPatientAndPsychologist(
        private val patientId: Long,
        private val psychologistId: Long
    ) : ConsultationFindingError(
        "Active consultation for patient $patientId and psychologist $psychologistId not found."
    )
}
