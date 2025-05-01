package ru.remsely.psyhosom.api.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile

@Schema(description = "Запрос на регистрацию психолога")
data class RegisterPsychologistRequest(
    @field:Schema(
        description = "Логин",
        example = "@username"
    )
    val username: String,

    @field:Schema(
        description = "Пароль",
        example = "password"
    )
    val password: String,

    @field:Schema(
        description = "Имя",
        example = "Анна"
    )
    val firstName: String,

    @field:Schema(
        description = "Фамилия",
        example = "Петрова"
    )
    val lastName: String,

    @field:Schema(
        description = "Фотография профиля",
        type = "string",
        format = "binary"
    )
    val profilePhoto: MultipartFile
)
