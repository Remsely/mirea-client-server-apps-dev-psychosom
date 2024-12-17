package ru.remsely.psyhosom.usecase.patient

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.Patient
import ru.remsely.psyhosom.domain.patient.event.UpdatePatientEvent

interface UpdatePatientCommand {
    fun execute(event: UpdatePatientEvent): Either<DomainError, Patient>
}

sealed class PatientUpdateError(override val message: String) : DomainError.BusinessLogicError {
    data class PatientNotFound(private val accountId: Long) : PatientUpdateError(
        "Profile for account with id $accountId not found."
    )

    data object PatientUsernameMustBeInContacts : PatientUpdateError(
        "User username must be present in contacts and cannot be changed."
    )
}
