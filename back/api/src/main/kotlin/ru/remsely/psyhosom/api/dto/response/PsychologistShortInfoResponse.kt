package ru.remsely.psyhosom.api.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Краткая информация оп психологе")
data class PsychologistShortInfoResponse(
    @field:Schema(
        description = "ID психолога",
        example = "1"
    )
    val id: Long,

    @field:Schema(
        description = "Имя психолога",
        example = "Анна"
    )
    val firstName: String,

    @field:Schema(
        description = "Фамилия психолога",
        example = "Щербакова"
    )
    val lastName: String,

    @field:Schema(
        description = "Ссылка на фото профиля",
        example = "https://aws.amazon.s3/profile_image.jpg"
    )
    val profileImage: String,

    @field:Schema(
        description = "Средний рейтинг психолога",
        example = "4.87"
    )
    val rating: Double?,
)
