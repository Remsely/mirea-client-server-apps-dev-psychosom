package ru.remsely.psyhosom.api.request

data class CreateReviewRequest(
    val rating: Int,
    val text: String,
)
