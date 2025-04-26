package ru.remsely.psyhosom.api.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Информация о пациенте")
data class PatientResponse(
    @field:Schema(
        description = "Логин пациента. Telegram username или номер телефона",
        example = "@username"
    )
    val username: String,

    @field:Schema(
        description = "Имя пациента",
        example = "Иван"
    )
    val firstName: String,

    @field:Schema(
        description = "Фамилия пациента",
        example = "Иванов"
    )
    val lastName: String,
)
