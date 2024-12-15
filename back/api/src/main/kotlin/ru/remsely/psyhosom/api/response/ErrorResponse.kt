package ru.remsely.psyhosom.api.response

import java.time.LocalDateTime

data class ErrorResponse(
    val message: String,
    val source: String,
    val timestamp: LocalDateTime,
    val status: String
)
