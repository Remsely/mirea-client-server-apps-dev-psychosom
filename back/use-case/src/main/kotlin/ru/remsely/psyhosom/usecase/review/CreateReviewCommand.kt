package ru.remsely.psyhosom.usecase.review

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.review.Review
import ru.remsely.psyhosom.domain.review.event.CreateReviewEvent

interface CreateReviewCommand {
    fun execute(event: CreateReviewEvent): Either<DomainError, Review>
}

sealed class ReviewCreationError(override val message: String) : DomainError.BusinessLogicError {
    data class ReviewForPsychologistAlreadyExists(
        private val psychologistId: Long,
        private val patientId: Long
    ) : ReviewCreationError(
        "Review from patient $patientId for psychologist $psychologistId already exist."
    )

    data class FinishedConsultationWithPsychologistNotFound(
        private val psychologistId: Long,
        private val patientId: Long
    ) : ReviewCreationError(
        "Finished consultation with psychologist $psychologistId for patient $patientId not found."
    )

    data class PatientPersonalDataNotFilled(
        private val patientId: Long
    ) : ReviewCreationError(
        "Patient $patientId personal data not filled."
    )
}
