package ru.remsely.psyhosom.api.response

import ru.remsely.psyhosom.domain.patient.Patient

data class PatientResponse(
    val username: String,
    val firstName: String,
    val lastName: String,
)

fun Patient.toResponse() = PatientResponse(
    username = account.username,
    firstName = firstName,
    lastName = lastName
)
