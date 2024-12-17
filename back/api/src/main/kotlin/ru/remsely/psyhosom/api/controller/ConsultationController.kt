package ru.remsely.psyhosom.api.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.remsely.psyhosom.api.response.CreateConsultationResponse
import ru.remsely.psyhosom.api.response.ErrorResponse
import ru.remsely.psyhosom.api.response.FindActiveConsultationsResponse
import ru.remsely.psyhosom.api.utils.annotation.AuthPatientId
import ru.remsely.psyhosom.domain.consultation.dao.ConsultationFindingError
import ru.remsely.psyhosom.domain.consultation.dao.ConsultationUpdater
import ru.remsely.psyhosom.domain.consultation.event.CreateConsultationEvent
import ru.remsely.psyhosom.domain.consultation.event.FindActiveConsultationsEvent
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.dao.PatientFindingError
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistFindingError
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.consultation.ConsultationCreationError
import ru.remsely.psyhosom.usecase.consultation.CreateConsultationCommand
import ru.remsely.psyhosom.usecase.consultation.FindActiveConsultationCommand
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/psychologists")
class ConsultationController(
    private val createConsultationCommand: CreateConsultationCommand,
    private val findActiveConsultationCommand: FindActiveConsultationCommand,
    private val consultationUpdater: ConsultationUpdater // TODO: убрать
) {
    private val log = logger()

    @PostMapping("/{psychologistId}/consultations")
    fun createConsultation(
        @AuthPatientId patientId: Long,
        @PathVariable psychologistId: Long,
    ): ResponseEntity<*> {
        log.info("POST /api/v1/consultations | patientId: $patientId.")
        return createConsultationCommand.execute(
            CreateConsultationEvent(
                patientId = patientId,
                psychologistId = psychologistId
            )
        ).fold(
            { handleError(it) },
            {
                ResponseEntity
                    .ok()
                    .body(
                        CreateConsultationResponse(
                            id = it.id,
                            psychologistId = it.psychologist.id,
                            patientId = it.patient.id,
                            status = it.status,
                            orderDate = it.orderDate
                        )
                    )
            }
        )
    }

    @GetMapping("/{psychologistId}/consultations/active")
    fun findActiveConsultation(
        @AuthPatientId patientId: Long,
        @PathVariable(required = true) psychologistId: Long
    ): ResponseEntity<*> {
        log.info("GET /api/v1/consultations/active | patientId: $patientId.")
        return findActiveConsultationCommand.execute(
            FindActiveConsultationsEvent(
                patientId = patientId,
                psychologistId = psychologistId
            )
        ).fold(
            { handleError(it) },
            {
                ResponseEntity
                    .ok()
                    .body(
                        FindActiveConsultationsResponse(
                            id = it.id,
                            psychologistId = it.psychologist.id,
                            patientId = it.patient.id,
                            status = it.status,
                            orderDate = it.orderDate,
                            confirmationDate = it.confirmationDate,
                            startDate = it.startDate
                        )
                    )
            }
        )
    }

    @PatchMapping("/{psychologistId}/consultations/{consultationId}/finish") // TODO: убрать
    fun finishConsultation(
        @AuthPatientId patientId: Long,
        @PathVariable(required = true) psychologistId: Long,
        @PathVariable(required = true) consultationId: Long
    ): ResponseEntity<*> {
        log.info("PATCH /api/v1/consultations/$consultationId/finish | patientId: $patientId.")
        return if (consultationUpdater.finishConsultation(consultationId)) {
            ResponseEntity.ok().body(mapOf("status" to "success"))
        } else {
            ResponseEntity.ok().body(mapOf("status" to "failed"))
        }
    }

    private fun handleError(error: DomainError): ResponseEntity<ErrorResponse> =
        when (error) {
            is ConsultationCreationError.ActiveConsultationExist -> HttpStatus.BAD_REQUEST
            is PatientFindingError.NotFoundById -> HttpStatus.BAD_REQUEST
            is PsychologistFindingError.NotFoundById -> HttpStatus.BAD_REQUEST
            is ConsultationFindingError.NotFoundActiveByPatientAndPsychologist -> HttpStatus.NOT_FOUND
            is ConsultationFindingError.MoreThanOneActiveFoundByPatientAndPsychologist -> HttpStatus.INTERNAL_SERVER_ERROR
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }.let {
            ResponseEntity
                .status(it)
                .body(
                    ErrorResponse(
                        message = error.message,
                        source = error.javaClass.name,
                        timestamp = LocalDateTime.now(),
                        status = it.name
                    )
                ).also {
                    log.warn(error.message)
                }
        }
}
