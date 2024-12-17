package ru.remsely.psyhosom.domain.review.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.review.Review

interface ReviewCreator {
    fun createReview(review: Review): Either<DomainError, Review>
}
