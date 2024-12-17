package ru.remsely.psyhosom.domain.consultation.event

data class FindActiveConsultationsEvent(
    val psychologistId: Long,
    val patientId: Long
)
