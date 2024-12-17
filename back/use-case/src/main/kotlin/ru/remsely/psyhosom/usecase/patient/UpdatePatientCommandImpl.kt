package ru.remsely.psyhosom.usecase.patient

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.account.Account
import ru.remsely.psyhosom.domain.account.dao.AccountFinder
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.Patient
import ru.remsely.psyhosom.domain.patient.dao.PatientFinder
import ru.remsely.psyhosom.domain.patient.dao.PatientUpdater
import ru.remsely.psyhosom.domain.patient.event.UpdatePatientEvent
import ru.remsely.psyhosom.domain.value_object.PhoneNumber
import ru.remsely.psyhosom.domain.value_object.TelegramUsername
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
            .flatMap {
                validatePossibleUsernameChange(event, it)
            }
            .flatMap { user ->
                patientFinder.findPatientByAccountId(user.id)
                    .fold(
                        {
                            log.error("Profile with for user with id ${event.accountId} not found.")
                            PatientUpdateError.PatientNotFound(event.accountId).left()
                        },
                        {
                            (user to it).right()
                        }
                    )
            }
            .flatMap { (user, profile) ->
                patientUpdater.updatePatient(
                    Patient(
                        id = profile.id,
                        account = user,
                        firstName = event.firstName ?: profile.firstName,
                        lastName = event.lastName ?: profile.lastName,
                        phone = if (event.phone?.value != null) event.phone else profile.phone,
                        telegram = if (event.telegram?.value != null) event.telegram else profile.telegram
                    )
                ).also {
                    log.info("Profile with id ${profile.id} successfully updated.")
                }
            }

    private fun validatePossibleUsernameChange(event: UpdatePatientEvent, account: Account) = either {
        if (event.phone?.value != null && PhoneNumber(account.username).isRight()) {
            ensure(account.username == event.phone?.value) {
                PatientUpdateError.PatientUsernameMustBeInContacts
            }
        }
        if (event.telegram?.value != null && TelegramUsername(account.username).isRight()) {
            ensure(account.username == event.telegram?.value) {
                PatientUpdateError.PatientUsernameMustBeInContacts
            }
        }
        account
    }
}
