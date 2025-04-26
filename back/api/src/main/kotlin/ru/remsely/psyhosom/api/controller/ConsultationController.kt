package ru.remsely.psyhosom.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.remsely.psyhosom.api.controller.open_api.ConsultationControllerContract
import ru.remsely.psyhosom.api.dto.request.CreateConsultationRequest
import ru.remsely.psyhosom.api.extensions.error_handling.toResponse
import ru.remsely.psyhosom.api.extensions.mapping.toDto
import ru.remsely.psyhosom.api.utils.annotations.AuthPatientId
import ru.remsely.psyhosom.domain.consultation.dao.ConsultationUpdater
import ru.remsely.psyhosom.domain.consultation.event.CreateConsultationEvent
import ru.remsely.psyhosom.domain.consultation.event.FindActiveConsultationsEvent
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.consultation.CreateConsultationCommand
import ru.remsely.psyhosom.usecase.consultation.FindActiveConsultationCommand

@RestController
@RequestMapping("/api/v1/psychologists")
class ConsultationController(
    private val createConsultationCommand: CreateConsultationCommand,
    private val findActiveConsultationCommand: FindActiveConsultationCommand,
    private val consultationUpdater: ConsultationUpdater // TODO: убрать
) : ConsultationControllerContract {
    private val log = logger()

    @PostMapping("/{psychologistId}/consultations")
    override fun createConsultation(
        @AuthPatientId patientId: Long,
        @PathVariable psychologistId: Long,
        @RequestBody request: CreateConsultationRequest
    ): ResponseEntity<*> {
        log.info("POST /api/v1/consultations | patientId: $patientId.")
        return createConsultationCommand.execute(
            CreateConsultationEvent(
                patientId = patientId,
                psychologistId = psychologistId,
                problemDescription = request.problemDescription,
                startDtTm = request.startDtTm,
                endDtTm = request.endDtTm,
            )
        ).fold(
            { err ->
                err.toResponse()
                    .also { log.warn(err.message) }
            },
            {
                ResponseEntity.ok(it.toDto())
            }
        )
    }

    @GetMapping("/{psychologistId}/consultations/active")
    override fun findActiveConsultation(
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
            { err ->
                err.toResponse()
                    .also { log.warn(err.message) }
            },
            {
                ResponseEntity.ok(it.toDto())
            }
        )
    }

    @PatchMapping("/{psychologistId}/consultations/{consultationId}/finish")
    override fun finishConsultation(
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
}
