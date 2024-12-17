package ru.remsely.psyhosom.api.response

import ru.remsely.psyhosom.domain.review.Review
import java.time.LocalDate

data class ReviewResponse(
    val patient: Patient,
    val psychologist: Psychologist,
    val id: Long,
    val rating: Int,
    val text: String,
    val date: LocalDate,
) {
    data class Psychologist(
        val id: Long
    )

    data class Patient(
        val id: Long,
        val firstName: String,
        val lastName: String
    )
}

fun Review.toResponse() = ReviewResponse(
    patient = ReviewResponse.Patient(
        id = patient.id,
        firstName = patient.firstName!!,
        lastName = patient.lastName!!
    ),
    psychologist = ReviewResponse.Psychologist(
        id = psychologist.id
    ),
    id = id,
    rating = rating.value,
    text = text,
    date = date
)
