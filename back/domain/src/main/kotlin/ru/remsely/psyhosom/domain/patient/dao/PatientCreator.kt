package ru.remsely.psyhosom.domain.patient.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.Patient

interface PatientCreator {
    fun createPatient(patient: Patient): Either<DomainError, Patient>
}
