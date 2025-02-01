package ru.remsely.psyhosom.api.response

import io.swagger.v3.oas.annotations.media.Schema
import ru.remsely.psyhosom.domain.consultation.Consultation
import java.time.LocalDateTime

@Schema(description = "Найденная консультация")
data class FindActiveConsultationsResponse(
    @field:Schema(
        description = "ID консультации",
        example = "1"
    )
    val id: Long,

    @field:Schema(
        description = "ID пациента",
        example = "1"
    )
    val patientId: Long,

    @field:Schema(
        description = "ID психолога",
        example = "1"
    )
    val psychologistId: Long,

    @field:Schema(
        description = "Статус консультации",
        example = "CONFIRMED"
    )
    val status: Consultation.Status,

    @field:Schema(
        description = "Дата и время записи на консультацию",
        example = "2025-02-01T16:43:50.2130582"
    )
    val orderDate: LocalDateTime,

    @field:Schema(
        description = "Дата и время подтверждения консультации",
        example = "2025-02-01T18:43:50.2130582"
    )
    val confirmationDate: LocalDateTime?,

    @field:Schema(
        description = "Дата и время начала консультации",
        example = "2025-02-03T13:20:00.0000000"
    )
    val startDate: LocalDateTime?
)
