package ru.remsely.psyhosom.usecase.review

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistFinder
import ru.remsely.psyhosom.domain.review.Review
import ru.remsely.psyhosom.domain.review.dao.ReviewFinder
import ru.remsely.psyhosom.monitoring.log.logger

@Component
open class FindPsychologistsReviewsCommandImpl(
    private val reviewFinder: ReviewFinder,
    private val psychologistFinder: PsychologistFinder
) : FindPsychologistsReviewsCommand {
    private val log = logger()

    @Transactional
    override fun execute(psychologistId: Long): Either<DomainError, List<Review>> =
        psychologistFinder.findPsychologistById(psychologistId)
            .flatMap {
                reviewFinder.findReviewsByPsychologistId(it.id).right()
            }.also {
                log.info("Reviews for psychologist with id $psychologistId successfully found.")
            }
}
