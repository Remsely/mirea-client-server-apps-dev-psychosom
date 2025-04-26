package ru.remsely.psyhosom.domain.value_object

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import ru.remsely.psyhosom.domain.error.DomainError

@JvmInline
value class ReviewRating private constructor(val value: Int) {
    companion object {
        operator fun invoke(value: Int): Either<DomainError.ValidationError, ReviewRating> = either {
            ensure(value in 1..5) {
                ReviewRatingValidationError.InvalidReviewRating
            }
            ReviewRating(value)
        }
    }
}

sealed class ReviewRatingValidationError(override val message: String) : DomainError.ValidationError {
    data object InvalidReviewRating : ReviewRatingValidationError(
        "Review rating must be between 1 and 5."
    )
}
