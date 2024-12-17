package ru.remsely.psyhosom.api.response

import ru.remsely.psyhosom.domain.patient.Patient

data class PatientResponse(
    val firstName: String?,
    val lastName: String?,
    val phone: String?,
    val telegram: String?
)

fun Patient.toResponse() = PatientResponse(
    phone = phone?.value,
    telegram = telegram?.value,
    firstName = firstName,
    lastName = lastName
)
