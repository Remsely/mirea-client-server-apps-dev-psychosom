package ru.remsely.psyhosom.api.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Сообщение об ошибке")
data class ErrorResponse(
    @field:Schema(
        description = "Описание ошибки",
        example = "Incorrect username or password."
    )
    val message: String,

    @field:Schema(
        description = "Место обнаружения ошибки",
        example = "ru.remsely.psyhosom.usecase.auth.UserLoginError\$AuthenticationError"
    )
    val source: String,

    @field:Schema(
        description = "Дата и время возникновения ошибки",
        pattern = "dd-MM-yyyy HH:mm:ss.SSS",
        example = "25-12-2025 13:00:00.000"
    )
    val timestamp: LocalDateTime,

    @field:Schema(
        description = "Название HTTP-статуса ошибки",
        example = "UNAUTHORIZED"
    )
    val status: String
)
