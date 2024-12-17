package ru.remsely.psyhosom.domain.consultation.event

data class CreateConsultationEvent(
    val patientId: Long,
    val psychologistId: Long
)
