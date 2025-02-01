package ru.remsely.psyhosom.api.response

import io.swagger.v3.oas.annotations.media.Schema
import ru.remsely.psyhosom.domain.consultation.Consultation
import java.time.LocalDateTime

@Schema(description = "Созданная консультация")
data class CreateConsultationResponse(
    @field:Schema(
        description = "ID консультации",
        example = "1"
    )
    val id: Long,

    @field:Schema(
        description = "ID психолога",
        example = "1"
    )
    val psychologistId: Long,

    @field:Schema(
        description = "ID пациента",
        example = "1"
    )
    val patientId: Long,

    @field:Schema(
        description = "Статус консультации",
        example = "PENDING"
    )
    val status: Consultation.Status,

    @field:Schema(
        description = "Дата и время записи на консультацию",
        example = "2025-02-01T15:32:07.2967943"
    )
    val orderDate: LocalDateTime,
)
