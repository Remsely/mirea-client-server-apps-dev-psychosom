package ru.remsely.psyhosom.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.remsely.psyhosom.api.controller.open_api.PatientControllerContract
import ru.remsely.psyhosom.api.dto.request.UpdatePatientRequest
import ru.remsely.psyhosom.api.extensions.error_handling.toResponse
import ru.remsely.psyhosom.api.extensions.mapping.toDto
import ru.remsely.psyhosom.api.utils.annotations.AuthAccountId
import ru.remsely.psyhosom.domain.patient.event.UpdatePatientEvent
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.patient.FindPatientCommand
import ru.remsely.psyhosom.usecase.patient.UpdatePatientCommand

@RestController
@RequestMapping("/api/v1/patients")
class PatientController(
    private val updatePatientCommand: UpdatePatientCommand,
    private val findPatientCommand: FindPatientCommand
) : PatientControllerContract {
    private val log = logger()

    @PutMapping
    override fun updatePatient(
        @AuthAccountId accountId: Long,
        @RequestBody request: UpdatePatientRequest
    ): ResponseEntity<*> {
        log.info("PUT /api/v1/patients | userId: $accountId.")
        return updatePatientCommand.execute(
            UpdatePatientEvent(
                accountId = accountId,
                firstName = request.firstName,
                lastName = request.lastName
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

    @GetMapping
    override fun findPatient(@AuthAccountId accountId: Long): ResponseEntity<*> {
        log.info("GET /api/v1/patients | userId: $accountId.")
        return findPatientCommand.execute(accountId)
            .fold(
                { err ->
                    err.toResponse()
                        .also { log.warn(err.message) }
                },
                {
                    ResponseEntity.ok(it.toDto())
                }
            )
    }
}
