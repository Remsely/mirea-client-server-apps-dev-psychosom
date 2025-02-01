package ru.remsely.psyhosom.api.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запрос на регистрацию пользователя")
data class RegisterRequest(
    @field:Schema(
        description = "Логин пользователя. Telegram username или номер телефона",
        example = "@username"
    )
    val username: String,

    @field:Schema(
        description = "Пароль пользователя",
        example = "123456"
    )
    val password: String,

    @field:Schema(
        description = "Имя пользователя",
        example = "Иван"
    )
    val firstName: String,

    @field:Schema(
        description = "Фамилия пользователя",
        example = "Иванов"
    )
    val lastName: String
) {
    override fun toString(): String {
        return "AuthRequest(login='$username')"
    }
}
