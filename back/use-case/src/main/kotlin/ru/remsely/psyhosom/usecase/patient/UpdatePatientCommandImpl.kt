package ru.remsely.psyhosom.usecase.patient

import arrow.core.Either
import arrow.core.flatMap
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.account.dao.AccountFinder
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.Patient
import ru.remsely.psyhosom.domain.patient.dao.PatientFinder
import ru.remsely.psyhosom.domain.patient.dao.PatientUpdater
import ru.remsely.psyhosom.domain.patient.event.UpdatePatientEvent
import ru.remsely.psyhosom.monitoring.log.logger

@Component
class UpdatePatientCommandImpl(
    private val accountFinder: AccountFinder,
    private val patientFinder: PatientFinder,
    private val patientUpdater: PatientUpdater,
) : UpdatePatientCommand {
    private val log = logger()

    override fun execute(event: UpdatePatientEvent): Either<DomainError, Patient> =
        accountFinder.findAccountById(event.accountId)
            .flatMap { user ->
                patientFinder.findPatientByAccountId(user.id)
                    .map { profile ->
                        (user to profile)
                    }
            }
            .flatMap { (user, profile) ->
                patientUpdater.updatePatient(
                    Patient(
                        id = profile.id,
                        account = user,
                        firstName = event.firstName ?: profile.firstName,
                        lastName = event.lastName ?: profile.lastName
                    )
                ).also {
                    log.info("Profile with id ${profile.id} successfully updated.")
                }
            }
}
