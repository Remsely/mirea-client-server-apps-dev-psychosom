package ru.remsely.psyhosom.api.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Запрос на создание отзыва")
data class CreateConsultationRequest(
    @field:Schema(
        description = "Дата и время начала консультации",
        pattern = "dd-MM-yyyy HH:mm:ss.SSS",
        example = "25-12-2025 13:00:00.000"
    )
    val startDtTm: LocalDateTime,

    @field:Schema(
        description = "Дата и время окончания консультации",
        pattern = "dd-MM-yyyy HH:mm:ss.SSS",
        example = "25-12-2025 13:00:00.000"
    )
    val endDtTm: LocalDateTime,

    @field:Schema(
        description = "Описание проблемы",
        example = "У меня проблемы со сном."
    )
    val problemDescription: String?,
)
