package ru.remsely.psyhosom.usecase.patient

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.Patient
import ru.remsely.psyhosom.domain.patient.event.UpdatePatientEvent

interface UpdatePatientCommand {
    fun execute(event: UpdatePatientEvent): Either<DomainError, Patient>
}

