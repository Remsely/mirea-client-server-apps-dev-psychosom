package ru.remsely.psyhosom.api.dto.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запрос на создание отзыва")
data class CreateReviewRequest(
    @field:Schema(
        description = "Рейтинг отзыва (от 1 до 5)",
        example = "5"
    )
    val rating: Int,

    @field:Schema(
        description = "Текст отзыва",
        example = "Отличный психолог, рекомендую!"
    )
    val text: String,
)
