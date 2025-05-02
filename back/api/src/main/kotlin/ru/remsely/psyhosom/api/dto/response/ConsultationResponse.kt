package ru.remsely.psyhosom.api.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import ru.remsely.psyhosom.api.dto.ScheduleSlotDto
import ru.remsely.psyhosom.domain.consultation.Consultation
import java.time.LocalDateTime

@Schema(description = "Найденная консультация")
data class ConsultationResponse(
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
        description = "Описание проблемы",
        example = "Меня мучает бессонница."
    )
    val problemDescription: String?,

    @field:Schema(
        description = "Слот в календаре"
    )
    val scheduleSlot: ScheduleSlotDto,

    @field:Schema(
        description = "Дата и время подачи заявки на консультацию",
        pattern = "dd-MM-yyyy HH:mm:ss.SSS",
        example = "25-12-2025 13:00:00.000"
    )
    val orderDtTm: LocalDateTime,

    @field:Schema(
        description = "Дата и время подтверждения консультации",
        pattern = "dd-MM-yyyy HH:mm:ss.SSS",
        example = "25-12-2025 13:00:00.000"
    )
    val confirmationDtTm: LocalDateTime?,
)
