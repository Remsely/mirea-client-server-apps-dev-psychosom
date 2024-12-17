package ru.remsely.psyhosom.db.dao

import arrow.core.*
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.remsely.psyhosom.db.extensions.toDomain
import ru.remsely.psyhosom.db.extensions.toEntity
import ru.remsely.psyhosom.db.repository.PatientRepository
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.Patient
import ru.remsely.psyhosom.domain.patient.dao.*
import ru.remsely.psyhosom.monitoring.log.logger
import kotlin.jvm.optionals.getOrNull

@Component
open class PatientDao(
    private val patientRepository: PatientRepository
) : PatientCreator, PatientFinder, PatientUpdater, PatientEraser {
    private val log = logger()

    @Transactional
    override fun createPatient(patient: Patient): Either<DomainError, Patient> =
        patientRepository.save(patient.toEntity())
            .toDomain()
            .right()
            .also {
                log.info("Patient with account ${patient.account.id} successfully created in DB.")
            }

    @Transactional(readOnly = true)
    override fun findPatientByAccountId(accountId: Long): Either<DomainError, Patient> =
        patientRepository.findByAccountId(accountId)
            .singleOrNone()
            .fold(
                { PatientFindingError.NotFoundByAccountId(accountId).left() },
                {
                    it.toDomain().right()
                        .also {
                            log.info("Patient with account $accountId successfully found in DB.")
                        }
                }
            )

    @Transactional(readOnly = true)
    override fun findPatientById(id: Long): Either<DomainError, Patient> =
        patientRepository.findById(id)
            .getOrNull()
            .toOption()
            .fold(
                { PatientFindingError.NotFoundById(id).left() },
                {
                    it.toDomain().right()
                        .also {
                            log.info("Patient with id $id successfully found in DB.")
                        }
                }
            )

    @Transactional(readOnly = true)
    override fun checkNotExistsWithUsernameInContacts(username: String): Either<DomainError, Unit> =
        either {
            ensure(
                !patientRepository.existsByTelegramEqualsIgnoreCaseOrPhoneEqualsIgnoreCase(
                    username,
                    username
                )
            ) {
                PatientFindingError.PatientWithUsernameAlreadyExists
            }
        }

    @Transactional
    override fun updatePatient(patient: Patient): Either<DomainError, Patient> =
        patientRepository.save(patient.toEntity())
            .toDomain()
            .right()
            .also {
                log.info("Patient with account ${patient.account.id} successfully updated in DB.")
            }

    @Transactional
    override fun erasePatientsByAccountIds(accountIds: List<Long>): Either<DomainError, Unit> =
        patientRepository.deleteByAccountIdIn(accountIds).right()
            .also {
                log.info("Patients by id list wish size ${accountIds.size} successfully deleted from DB.")
            }
}
