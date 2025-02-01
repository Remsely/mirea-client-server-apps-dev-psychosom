package ru.remsely.psyhosom.domain.patient.event

data class UpdatePatientEvent(
    val accountId: Long,
    val firstName: String?,
    val lastName: String?
)
