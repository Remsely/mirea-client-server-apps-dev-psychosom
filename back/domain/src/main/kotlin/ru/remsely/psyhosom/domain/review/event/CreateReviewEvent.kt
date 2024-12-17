package ru.remsely.psyhosom.domain.review.event

import ru.remsely.psyhosom.domain.value_object.ReviewRating

data class CreateReviewEvent(
    val patientId: Long,
    val psychologistId: Long,
    val text: String,
    val rating: ReviewRating
)
