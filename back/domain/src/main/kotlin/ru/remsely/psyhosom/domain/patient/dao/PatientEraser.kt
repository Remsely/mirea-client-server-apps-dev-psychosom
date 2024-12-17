package ru.remsely.psyhosom.domain.patient.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError

interface PatientEraser {
    fun erasePatientsByAccountIds(accountIds: List<Long>): Either<DomainError, Unit>
}
