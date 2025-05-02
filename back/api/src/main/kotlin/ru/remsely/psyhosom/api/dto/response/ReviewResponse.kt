package ru.remsely.psyhosom.api.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(description = "Отзыв")
data class ReviewResponse(
    @field:Schema(
        description = "Информация о пациенте",
    )
    val patient: Patient,

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
