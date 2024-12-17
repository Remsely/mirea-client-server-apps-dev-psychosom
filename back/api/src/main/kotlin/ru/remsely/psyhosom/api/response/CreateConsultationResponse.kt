package ru.remsely.psyhosom.api.response

import ru.remsely.psyhosom.domain.consultation.Consultation
import java.time.LocalDateTime

data class CreateConsultationResponse(
    val id: Long,
    val psychologistId: Long,
    val patientId: Long,
    val status: Consultation.Status,
    val orderDate: LocalDateTime,
)
