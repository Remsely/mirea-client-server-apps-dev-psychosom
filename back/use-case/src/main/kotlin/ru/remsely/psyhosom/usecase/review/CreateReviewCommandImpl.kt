package ru.remsely.psyhosom.usecase.review

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.remsely.psyhosom.domain.consultation.dao.ConsultationFinder
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.dao.PatientFinder
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistFinder
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistUpdater
import ru.remsely.psyhosom.domain.review.Review
import ru.remsely.psyhosom.domain.review.dao.ReviewFinder
import ru.remsely.psyhosom.domain.review.event.CreateReviewEvent
import ru.remsely.psyhosom.monitoring.log.logger
import java.time.LocalDate

@Component
open class CreateReviewCommandImpl(
    private val psychologistFinder: PsychologistFinder,
    private val psychologistUpdater: PsychologistUpdater,
    private val patientFinder: PatientFinder,
    private val consultationFinder: ConsultationFinder,
    private val reviewFinder: ReviewFinder
) : CreateReviewCommand {
    private val log = logger()

    @Transactional
    override fun execute(event: CreateReviewEvent): Either<DomainError, Review> = either {
        val patient = patientFinder.findPatientById(event.patientId).bind()

        val psychologist = psychologistFinder.findPsychologistById(event.psychologistId).bind()

        ensure(
            !reviewFinder.existReviewByPatientIdAndPsychologistId(
                patientId = patient.id,
                psychologistId = psychologist.id
            )
        ) {
            ReviewCreationValidationError.ReviewForPsychologistAlreadyExists(
                patientId = patient.id,
                psychologistId = psychologist.id
            )
        }

        ensure(
            consultationFinder.existFinishedConsultationByPatientAndPsychologist(
                patientId = patient.id,
                psychologistId = psychologist.id
            )
        ) {
            ReviewCreationValidationError.FinishedConsultationWithPsychologistNotFound(
                patientId = patient.id,
                psychologistId = psychologist.id
            )
        }

        val review = Review(
            id = 0L,
            patient = patient,
            text = event.text,
            rating = event.rating,
            date = LocalDate.now(),
        )

        psychologistUpdater.updatePsychologist(
            psychologist.copy(
                reviews = psychologist.reviews + review
            )
        ).bind().let { psy ->
            psy.reviews.maxBy { it.date }
        }.also {
            log.info("Review with id ${review.id} successfully created.")
        }
    }
}
