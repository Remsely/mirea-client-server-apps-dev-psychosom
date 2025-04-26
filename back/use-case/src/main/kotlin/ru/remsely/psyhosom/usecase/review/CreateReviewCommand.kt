package ru.remsely.psyhosom.usecase.review

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.review.Review
import ru.remsely.psyhosom.domain.review.event.CreateReviewEvent

interface CreateReviewCommand {
    fun execute(event: CreateReviewEvent): Either<DomainError, Review>
}

sealed class ReviewCreationValidationError(override val message: String) : DomainError.ValidationError {
    data class ReviewForPsychologistAlreadyExists(
        private val psychologistId: Long,
        private val patientId: Long
    ) : ReviewCreationValidationError(
        "Review from patient $patientId for psychologist $psychologistId already exist."
    )

    data class FinishedConsultationWithPsychologistNotFound(
        private val psychologistId: Long,
        private val patientId: Long
    ) : ReviewCreationValidationError(
        "Finished consultation with psychologist $psychologistId for patient $patientId not found."
    )
}
