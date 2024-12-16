package ru.remsely.psyhosom.api.response

data class RegisterResponse(
    val tbBotConfirmationUrl: String,
    val webSocketToken: String
)
