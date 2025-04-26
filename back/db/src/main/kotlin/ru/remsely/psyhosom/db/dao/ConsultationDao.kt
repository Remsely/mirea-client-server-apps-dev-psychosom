package ru.remsely.psyhosom.db.dao

import arrow.core.*
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.remsely.psyhosom.db.extensions.toDomain
import ru.remsely.psyhosom.db.extensions.toEntity
import ru.remsely.psyhosom.db.repository.ConsultationRepository
import ru.remsely.psyhosom.domain.consultation.Consultation
import ru.remsely.psyhosom.domain.consultation.dao.*
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.monitoring.log.logger
import kotlin.jvm.optionals.getOrNull

@Component
open class ConsultationDao(
    private val repository: ConsultationRepository
) : ConsultationCreator, ConsultationUpdater, ConsultationFinder {
    private val log = logger()

    @Transactional
    override fun createConsultation(consultation: Consultation): Either<DomainError, Consultation> =
        repository.save(consultation.toEntity())
            .toDomain()
            .right()
            .also {
                log.info("Consultation with id ${consultation.id} successfully created in DB.")
            }

    @Transactional(readOnly = true)
    override fun findConsultationById(consultationId: Long): Either<DomainError, Consultation> =
        repository.findById(consultationId)
            .getOrNull()
            .toOption()
            .fold(
                {
                    ConsultationMissingError.NotFoundById(consultationId).left()
                },
                {
                    it.toDomain().right().also {
                        log.info("Consultation with id $consultationId successfully found in DB.")
                    }
                }
            )

    @Transactional(readOnly = true)
    override fun existActiveConsultationByPatientAndPsychologist(
        patientId: Long,
        psychologistId: Long
    ): Boolean = repository.existsByPatientIdAndPsychologistIdAndStatusNotIn(
        patientId = patientId,
        psychologistId = psychologistId,
        statuses = listOf(Consultation.Status.FINISHED, Consultation.Status.CANCELED)
    ).also {
        log.info(
            if (it)
                "Active consultation for patient with id $patientId and psychologist with id $psychologistId exist in DB."
            else
                "Active consultation for patient with id $patientId and psychologist with id $psychologistId not exist in DB."
        )
    }

    @Transactional(readOnly = true)
    override fun existFinishedConsultationByPatientAndPsychologist(patientId: Long, psychologistId: Long): Boolean =
        repository.existsByPatientIdAndPsychologistIdAndStatus(
            patientId = patientId,
            psychologistId = psychologistId,
            status = Consultation.Status.FINISHED
        ).also {
            log.info(
                if (it)
                    "Consultation for patient with id $patientId and psychologist with id $psychologistId exist in DB."
                else
                    "Consultation for patient with id $patientId and psychologist with id $psychologistId not exist in DB."
            )
        }

    @Transactional(readOnly = true)
    override fun findActiveSessionByPatientIdAndPsychologistId(
        patientId: Long,
        psychologistId: Long
    ): Either<DomainError, Consultation> =
        repository.findByPatientIdAndPsychologistIdAndStatusNotIn(
            patientId = patientId,
            psychologistId = psychologistId,
            statuses = listOf(Consultation.Status.FINISHED, Consultation.Status.CANCELED)
        ).toNonEmptyListOrNone()
            .fold(
                {
                    ConsultationMissingError.NotFoundActiveByPatientAndPsychologist(
                        patientId = patientId,
                        psychologistId = psychologistId
                    ).left()
                },
                { list ->
                    if (list.size > 1) {
                        ConsultationFindingError.MoreThanOneActiveFoundByPatientAndPsychologist(
                            patientId = patientId,
                            psychologistId = psychologistId
                        ).left()
                    } else {
                        list.single().toDomain().also {
                            log.info(
                                "Active consultation for patient with id $patientId and psychologist with id " +
                                        "$psychologistId found in DB."
                            )
                        }.right()
                    }
                }
            )

    @Transactional
    override fun cancelConsultation(consultation: Consultation): Either<DomainError, Consultation> = either {
        ensure(consultation.status in listOf(Consultation.Status.PENDING, Consultation.Status.CONFIRMED)) {
            ConsultationUpdateError.WrongStatusToCancel(consultation.status)
        }
        repository.save(
            consultation.copy(
                status = Consultation.Status.CANCELED
            ).toEntity()
        ).toDomain()
    }

    // TODO: убрать
    @Transactional
    override fun finishConsultation(consultationId: Long): Boolean =
        findConsultationById(consultationId)
            .fold(
                { false },
                {
                    repository.save(
                        it.copy(
                            status = Consultation.Status.FINISHED
                        ).toEntity()
                    )
                    true
                }
            )
}
