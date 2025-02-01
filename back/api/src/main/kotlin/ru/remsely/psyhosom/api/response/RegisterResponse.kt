package ru.remsely.psyhosom.api.response

data class RegisterResponse(
    val accountConfirmationUrl: String,
    val webSocketToken: String
)
