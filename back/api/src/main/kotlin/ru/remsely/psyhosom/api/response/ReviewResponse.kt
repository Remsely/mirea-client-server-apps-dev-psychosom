package ru.remsely.psyhosom.api.response

import io.swagger.v3.oas.annotations.media.Schema
import ru.remsely.psyhosom.domain.review.Review
import java.time.LocalDate

@Schema(description = "Отзыв")
data class ReviewResponse(
    @field:Schema(
        description = "Информация о пациенте",
    )
    val patient: Patient,

    @field:Schema(
        description = "Информация о психологе",
    )
    val psychologist: Psychologist,

    @field:Schema(
        description = "ID отзыва",
        example = "1"
    )
    val id: Long,

    @field:Schema(
        description = "Рейтинг отзыва (от 1 до 5)",
        example = "5"
    )
    val rating: Int,

    @field:Schema(
        description = "Текст отзыва",
        example = "Хороший психолог!"
    )
    val text: String,

    @field:Schema(
        description = "Дата создания отзыва",
        example = "2025-01-01"
    )
    val date: LocalDate,
) {
    data class Psychologist(
        @field:Schema(
            description = "ID психолога",
            example = "1"
        )
        val id: Long,

        @field:Schema(
            description = "Имя психолога",
            example = "Иван"
        )
        val firstName: String,

        @field:Schema(
            description = "Фамилия психолога",
            example = "Иванов"
        )
        val lastName: String
    )

    data class Patient(
        @field:Schema(
            description = "ID пациента",
            example = "1"
        )
        val id: Long,

        @field:Schema(
            description = "Имя пациента",
            example = "Иван"
        )
        val firstName: String,

        @field:Schema(
            description = "Фамилия пациента",
            example = "Иванов"
        )
        val lastName: String
    )
}

fun Review.toResponse() = ReviewResponse(
    patient = ReviewResponse.Patient(
        id = patient.id,
        firstName = patient.firstName,
        lastName = patient.lastName
    ),
    psychologist = ReviewResponse.Psychologist(
        id = psychologist.id,
        firstName = psychologist.firstName,
        lastName = psychologist.lastName
    ),
    id = id,
    rating = rating.value,
    text = text,
    date = date
)
