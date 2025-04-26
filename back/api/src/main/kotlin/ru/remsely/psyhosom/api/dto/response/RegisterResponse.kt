package ru.remsely.psyhosom.api.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Ответ на запрос на регистрацию")
data class RegisterResponse(
    @field:Schema(
        description = "Ссылка для подтверждения регистрации через Telegram",
        example = "https://t.me/bot_name?start=tg-k4W39j2hAp-0102251519"
    )
    val accountConfirmationUrl: String,

    @field:Schema(
        description = "Токен для подключения к WebSocket сессии",
        example = "tg-k4W39j2hAp-0102251519"
    )
    val webSocketToken: String
)
