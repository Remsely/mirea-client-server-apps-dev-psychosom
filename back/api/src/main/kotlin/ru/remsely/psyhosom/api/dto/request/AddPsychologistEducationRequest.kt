package ru.remsely.psyhosom.api.dto.request

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile

@Schema(description = "Информация об образовании психолога")
data class AddPsychologistEducationRequest(
    @field:ArraySchema(
        arraySchema = Schema(
            description = "Файлы, подтверждающие образование"
        ),
        schema = Schema(
            type = "string",
            format = "binary"
        )
    )
    val files: List<MultipartFile>
)
