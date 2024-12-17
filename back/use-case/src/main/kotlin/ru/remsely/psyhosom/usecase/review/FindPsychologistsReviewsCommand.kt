package ru.remsely.psyhosom.usecase.review

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.review.Review

interface FindPsychologistsReviewsCommand {
    fun execute(psychologistId: Long): Either<DomainError, List<Review>>
}
