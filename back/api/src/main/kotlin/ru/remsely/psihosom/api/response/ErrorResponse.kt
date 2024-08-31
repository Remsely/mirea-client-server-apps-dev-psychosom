package ru.remsely.psihosom.api.response

import java.time.LocalDateTime

data class ErrorResponse(
    val message: String,
    val timestamp: LocalDateTime,
    val status: String
)
