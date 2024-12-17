package ru.remsely.psyhosom.db.dao


import arrow.core.Either
import arrow.core.right
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.remsely.psyhosom.db.extensions.toDomain
import ru.remsely.psyhosom.db.extensions.toEntity
import ru.remsely.psyhosom.db.repository.ReviewRepository
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.review.Review
import ru.remsely.psyhosom.domain.review.dao.ReviewCreator
import ru.remsely.psyhosom.domain.review.dao.ReviewFinder
import ru.remsely.psyhosom.monitoring.log.logger

@Component
open class ReviewDao(
    private val repository: ReviewRepository
) : ReviewCreator, ReviewFinder {
    private val log = logger()

    @Transactional(readOnly = true)
    override fun existReviewByPatientIdAndPsychologistId(patientId: Long, psychologistId: Long): Boolean =
        repository.existsByPatientIdAndPsychologistId(
            patientId = patientId,
            psychologistId = psychologistId
        ).also {
            log.info(
                if (it)
                    "Review for patient with id $patientId and psychologist with id $psychologistId exist in DB."
                else
                    "Review for patient with id $patientId and psychologist with id $psychologistId not exist in DB."
            )
        }

    @Transactional(readOnly = true)
    override fun findReviewsByPsychologistId(psychologistId: Long): List<Review> =
        repository.findByPsychologistId(psychologistId)
            .map {
                it.toDomain()
            }.also {
                log.info("${it.size} reviews for psychologist with id $psychologistId found in DB.")
            }

    @Transactional
    override fun createReview(review: Review): Either<DomainError, Review> =
        repository.save(review.toEntity())
            .toDomain()
            .right()
            .also {
                log.info("Review with id ${review.id} successfully created in DB.")
            }
}
