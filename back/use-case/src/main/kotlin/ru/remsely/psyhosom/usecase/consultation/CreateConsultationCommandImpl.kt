package ru.remsely.psyhosom.usecase.consultation

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.singleOrNone
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.remsely.psyhosom.domain.consultation.Consultation
import ru.remsely.psyhosom.domain.consultation.dao.ConsultationCreator
import ru.remsely.psyhosom.domain.consultation.dao.ConsultationFinder
import ru.remsely.psyhosom.domain.consultation.event.CreateConsultationEvent
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.dao.PatientFinder
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistFinder
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistUpdater
import ru.remsely.psyhosom.domain.schedule.Schedule
import ru.remsely.psyhosom.domain.utils.replaceAllIf
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.telegram.NotificationEvent
import java.time.LocalDateTime

@Component
open class CreateConsultationCommandImpl(
    private val consultationCreator: ConsultationCreator,
    private val consultationFinder: ConsultationFinder,
    private val psychologistFinder: PsychologistFinder,
    private val psychologistUpdater: PsychologistUpdater,
    private val patientFinder: PatientFinder,
    private val eventPublisher: ApplicationEventPublisher
) : CreateConsultationCommand {
    private val log = logger()

    @Transactional
    override fun execute(event: CreateConsultationEvent): Either<DomainError, Consultation> = either {
        val psychologist = psychologistFinder.findPsychologistById(event.psychologistId).bind()
        val patient = patientFinder.findPatientById(event.patientId).bind()

        val psychologistSlots = psychologist.schedule.values

        ensure(
            !consultationFinder.existActiveConsultationByPatientAndPsychologist(
                patientId = patient.id,
                psychologistId = psychologist.id
            )
        ) {
            ConsultationCreationValidationError.ActiveConsultationExist(
                psychologistId = psychologist.id,
                patientId = patient.id
            )
        }

        val bookedSlot = psychologistSlots.singleOrNone {
            it.date == event.date && it.startTm == event.startTm && it.endTm == event.endTm && it.available
        }.getOrElse {
            raise(ConsultationCreationValidationError.SlotIsUnavailable)
        }.copy(
            available = false
        )

        val updatedSlots = psychologistSlots.replaceAllIf({ it.id == bookedSlot.id }, { bookedSlot })

        psychologistUpdater.updatePsychologist(
            psychologist.copy(
                schedule = Schedule(updatedSlots)
            )
        )

        val consultation = consultationCreator.createConsultation(
            Consultation(
                id = 0L,
                patient = patient,
                psychologist = psychologist,
                problemDescription = event.problemDescription,
                scheduleSlot = bookedSlot,
                status = Consultation.Status.PENDING,
                orderDtTm = LocalDateTime.now(),
                confirmationDtTm = null,
                meetingLink = null
            )
        ).bind()

        log.info(
            "Session for patient with id ${patient.id} and " +
                    "psychologist with id ${psychologist.id} successfully created."
        )

        eventPublisher.publishEvent(
            NotificationEvent.ConsultationCreated(consultation)
        )

        consultation
    }
}
