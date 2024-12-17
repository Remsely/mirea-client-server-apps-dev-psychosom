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
import ru.remsely.psyhosom.domain.review.Review
import ru.remsely.psyhosom.domain.review.dao.ReviewCreator
import ru.remsely.psyhosom.domain.review.dao.ReviewFinder
import ru.remsely.psyhosom.domain.review.event.CreateReviewEvent
import ru.remsely.psyhosom.monitoring.log.logger
import java.time.LocalDate

@Component
open class CreateReviewCommandImpl(
    private val psychologistFinder: PsychologistFinder,
    private val patientFinder: PatientFinder,
    private val consultationFinder: ConsultationFinder,
    private val reviewFinder: ReviewFinder,
    private val reviewCreator: ReviewCreator
) : CreateReviewCommand {
    private val log = logger()

    @Transactional
    override fun execute(event: CreateReviewEvent): Either<DomainError, Review> = either {
        val patient = patientFinder.findPatientById(event.patientId).bind()

        ensure(patient.firstName != null && patient.lastName != null) {
            ReviewCreationError.PatientPersonalDataNotFilled(patientId = patient.id)
        }

        val psychologist = psychologistFinder.findPsychologistById(event.psychologistId).bind()

        ensure(
            !reviewFinder.existReviewByPatientIdAndPsychologistId(
                patientId = patient.id,
                psychologistId = psychologist.id
            )
        ) {
            ReviewCreationError.ReviewForPsychologistAlreadyExists(
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
            ReviewCreationError.FinishedConsultationWithPsychologistNotFound(
                patientId = patient.id,
                psychologistId = psychologist.id
            )
        }

        reviewCreator.createReview(
            Review(
                id = 0L,
                patient = patient,
                psychologist = psychologist,
                text = event.text,
                rating = event.rating,
                date = LocalDate.now(),
            )
        ).bind().also {
            log.info("Review with id ${it.id} successfully created.")
        }
    }
}