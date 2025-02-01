package ru.remsely.psyhosom.api.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Токен авторизации")
data class LoginResponse(
    @field:Schema(description = "JWT")
    val token: String
)
