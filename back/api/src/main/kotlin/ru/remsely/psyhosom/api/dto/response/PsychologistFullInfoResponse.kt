package ru.remsely.psyhosom.api.dto.response

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import ru.remsely.psyhosom.api.dto.ArticleBlockDto

@Schema(description = "Информация о психологе")
data class PsychologistFullInfoResponse(
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
        description = "Статья профиля",
    )
    val article: List<ArticleBlockDto>?,

    @ArraySchema(
        arraySchema = Schema(
            description = "Список файлов, подтверждающих квалификацию психолога"
        ),
        schema = Schema(
            type = "string",
            format = "uri",
            example = "https://aws.amazon.s3/edu_image1.jpg"
        )
    )
    val educationFiles: List<String>?,
)
