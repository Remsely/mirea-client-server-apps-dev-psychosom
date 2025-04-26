package ru.remsely.psyhosom.domain.patient.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.Patient

interface PatientFinder {
    fun findPatientByAccountId(accountId: Long): Either<DomainError, Patient>

    fun findPatientById(id: Long): Either<DomainError, Patient>
}

sealed class PatientMissingError(override val message: String) : DomainError.MissingError {
    data class NotFoundByAccountId(private val accountId: Long) : PatientMissingError(
        "Patient with for account $accountId not found."
    )

    data class NotFoundById(private val id: Long) : PatientMissingError(
        "Patient with id $id not found."
    )
}
