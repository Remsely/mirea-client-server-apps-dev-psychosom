package ru.remsely.psyhosom.domain.patient.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.Patient

interface PatientFinder {
    fun findPatientByAccountId(accountId: Long): Either<DomainError, Patient>

    fun findPatientById(id: Long): Either<DomainError, Patient>

    fun checkNotExistsWithUsernameInContacts(username: String): Either<DomainError, Unit>
}

sealed class PatientFindingError(override val message: String) : DomainError.ValidationError {
    data class NotFoundByAccountId(private val accountId: Long) : PatientFindingError(
        "Patient with for account $accountId not found."
    )

    data class NotFoundById(private val id: Long) : PatientFindingError(
        "Patient with id $id not found."
    )

    data object PatientWithUsernameAlreadyExists : PatientFindingError(
        "Patient with such username already exists."
    )
}
