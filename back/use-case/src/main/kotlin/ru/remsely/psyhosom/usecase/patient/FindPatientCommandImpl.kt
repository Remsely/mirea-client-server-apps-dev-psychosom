package ru.remsely.psyhosom.usecase.patient

import arrow.core.Either
import arrow.core.flatMap
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.account.dao.AccountFinder
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.Patient
import ru.remsely.psyhosom.domain.patient.dao.PatientFinder
import ru.remsely.psyhosom.monitoring.log.logger

@Component
class FindPatientCommandImpl(
    private val accountFinder: AccountFinder,
    private val patientFinder: PatientFinder
) : FindPatientCommand {
    private val log = logger()

    override fun execute(userId: Long): Either<DomainError, Patient> =
        accountFinder.findAccountById(userId)
            .flatMap {
                patientFinder.findPatientByAccountId(userId)
            }.also {
                log.info("Profile for user with id $userId successfully found.")
            }
}
