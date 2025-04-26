package ru.remsely.psyhosom.api.dto.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запрос на авторизацию")
data class LoginRequest(
    @field:Schema(
        description = "Логин пользователя. Telegram username или номер телефона",
        example = "@username"
    )
    val username: String,

    @field:Schema(
        description = "Пароль",
        example = "123456"
    )
    val password: String
) {
    override fun toString(): String {
        return "AuthRequest(login='$username')"
    }
}
