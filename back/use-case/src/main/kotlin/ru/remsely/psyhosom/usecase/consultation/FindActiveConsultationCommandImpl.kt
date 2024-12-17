package ru.remsely.psyhosom.usecase.consultation

import arrow.core.Either
import arrow.core.raise.either
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.remsely.psyhosom.domain.consultation.Consultation
import ru.remsely.psyhosom.domain.consultation.dao.ConsultationFinder
import ru.remsely.psyhosom.domain.consultation.event.FindActiveConsultationsEvent
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.dao.PatientFinder
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistFinder
import ru.remsely.psyhosom.monitoring.log.logger

@Component
open class FindActiveConsultationCommandImpl(
    private val consultationFinder: ConsultationFinder,
    private val patientFinder: PatientFinder,
    private val psychologistFinder: PsychologistFinder
) : FindActiveConsultationCommand {
    private val log = logger()

    @Transactional(readOnly = true)
    override fun execute(
        event: FindActiveConsultationsEvent
    ): Either<DomainError, Consultation> = either {
        val patient = patientFinder
            .findPatientById(event.patientId)
            .bind()

        val psychologist = psychologistFinder
            .findPsychologistById(event.psychologistId)
            .bind()

        consultationFinder.findActiveSessionByPatientIdAndPsychologistId(
            patient.id,
            psychologist.id
        ).also {
            log.info(
                "Sessions for patient with id ${patient.id} and psychologist with id ${psychologist.id} " +
                        "successfully found."
            )
        }.bind()
    }
}
