package ru.remsely.psyhosom.api.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.remsely.psyhosom.api.controller.open_api.PatientControllerContract
import ru.remsely.psyhosom.api.request.UpdatePatientRequest
import ru.remsely.psyhosom.api.response.ErrorResponse
import ru.remsely.psyhosom.api.response.toResponse
import ru.remsely.psyhosom.api.utils.annotation.AuthAccountId
import ru.remsely.psyhosom.domain.account.dao.AccountFindingError
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.dao.PatientFindingError
import ru.remsely.psyhosom.domain.patient.event.UpdatePatientEvent
import ru.remsely.psyhosom.domain.value_object.PhoneNumberValidationError
import ru.remsely.psyhosom.domain.value_object.TelegramUsernameValidationError
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.patient.FindPatientCommand
import ru.remsely.psyhosom.usecase.patient.PatientUpdateError
import ru.remsely.psyhosom.usecase.patient.UpdatePatientCommand
import java.time.LocalDateTime

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
            { handleError(it) },
            {
                ResponseEntity
                    .ok()
                    .body(it.toResponse())
            }
        )
    }

    @GetMapping
    override fun findPatient(@AuthAccountId accountId: Long): ResponseEntity<*> {
        log.info("GET /api/v1/patients | userId: $accountId.")
        return findPatientCommand.execute(accountId)
            .fold(
                { handleError(it) },
                {
                    ResponseEntity
                        .ok()
                        .body(it.toResponse())
                }
            )
    }

    private fun handleError(error: DomainError): ResponseEntity<ErrorResponse> =
        when (error) {
            is PhoneNumberValidationError.InvalidPhoneNumber -> HttpStatus.BAD_REQUEST
            is TelegramUsernameValidationError.InvalidTelegramUsername -> HttpStatus.BAD_REQUEST
            is PatientUpdateError.PatientUsernameMustBeInContacts -> HttpStatus.BAD_REQUEST
            is AccountFindingError.NotFoundById -> HttpStatus.BAD_REQUEST
            is PatientFindingError.NotFoundByAccountId -> HttpStatus.BAD_REQUEST
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
