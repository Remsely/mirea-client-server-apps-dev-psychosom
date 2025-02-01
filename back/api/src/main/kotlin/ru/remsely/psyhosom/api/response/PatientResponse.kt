package ru.remsely.psyhosom.api.response

import io.swagger.v3.oas.annotations.media.Schema
import ru.remsely.psyhosom.domain.patient.Patient

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

fun Patient.toResponse() = PatientResponse(
    username = account.username,
    firstName = firstName,
    lastName = lastName
)
