package ru.remsely.psyhosom.api.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запрос на обновление данных пациента")
data class UpdatePatientRequest(
    @field:Schema(
        description = "Имя пациента",
        example = "Иван"
    )
    val firstName: String?,

    @field:Schema(
        description = "Фамилия пациента",
        example = "Иванов"
    )
    val lastName: String?
) {
    override fun toString(): String {
        return "UpdateUserProfileRequest()"
    }
}
