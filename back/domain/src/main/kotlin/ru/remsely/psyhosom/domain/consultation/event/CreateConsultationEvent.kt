package ru.remsely.psyhosom.domain.consultation.event

import java.time.LocalDate
import java.time.LocalTime

data class CreateConsultationEvent(
    val patientId: Long,
    val psychologistId: Long,
    val problemDescription: String?,
    val date: LocalDate,
    val startTm: LocalTime,
    val endTm: LocalTime,
)
