package ru.remsely.psyhosom.domain.review

import ru.remsely.psyhosom.domain.patient.Patient
import ru.remsely.psyhosom.domain.value_object.ReviewRating
import java.time.LocalDate

data class Review(
    val id: Long,
    val patient: Patient,
    val rating: ReviewRating,
    val text: String,
    val date: LocalDate
)
