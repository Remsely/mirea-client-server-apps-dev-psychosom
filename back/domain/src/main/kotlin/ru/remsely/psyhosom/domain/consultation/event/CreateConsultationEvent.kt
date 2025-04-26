package ru.remsely.psyhosom.domain.consultation.event

import java.time.LocalDateTime

data class CreateConsultationEvent(
    val patientId: Long,
    val psychologistId: Long,
    val problemDescription: String?,
    val startDtTm: LocalDateTime,
    val endDtTm: LocalDateTime,
)
