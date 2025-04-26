package ru.remsely.psyhosom.usecase.consultation

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.remsely.psyhosom.domain.consultation.Consultation
import ru.remsely.psyhosom.domain.consultation.dao.ConsultationCreator
import ru.remsely.psyhosom.domain.consultation.dao.ConsultationFinder
import ru.remsely.psyhosom.domain.consultation.event.CreateConsultationEvent
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.dao.PatientFinder
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistFinder
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.telegram.BotNotifyWithCancelConsultationButtonCommand
import java.time.LocalDateTime

@Component
open class CreateConsultationCommandImpl(
    private val consultationCreator: ConsultationCreator,
    private val consultationFinder: ConsultationFinder,
    private val psychologistFinder: PsychologistFinder,
    private val patientFinder: PatientFinder,
    private val telegramNotifyCommand: BotNotifyWithCancelConsultationButtonCommand
) : CreateConsultationCommand {
    private val log = logger()

    @Transactional
    override fun execute(event: CreateConsultationEvent): Either<DomainError, Consultation> = either {
        val patientId = event.patientId
        val psychologistId = event.psychologistId

        val psychologist = psychologistFinder.findPsychologistById(psychologistId).bind()

        ensure(!consultationFinder.existActiveConsultationByPatientAndPsychologist(patientId, psychologistId)) {
            ConsultationCreationValidationError.ActiveConsultationExist(patientId, psychologistId)
        }

        val patient = patientFinder.findPatientById(patientId).bind()

        val consultation = consultationCreator.createConsultation(
            Consultation(
                id = 0L,
                patient = patient,
                psychologist = psychologist,
                problemDescription = event.problemDescription,
                period = Consultation.Period(
                    start = event.startDtTm,
                    end = event.endDtTm
                ).bind(),
                status = Consultation.Status.PENDING,
                orderDtTm = LocalDateTime.now(),
                confirmationDtTm = null
            )
        ).bind()

        log.info(
            "Session for patient with id $patientId and psychologist with id $psychologistId " +
                    "successfully created."
        )

        telegramNotifyCommand.sendMessage(
            chatId = patient.account.tgChatId,
            consultation = consultation
        )

        consultation
    }
}
