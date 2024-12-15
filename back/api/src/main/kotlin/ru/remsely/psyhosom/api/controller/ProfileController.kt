package ru.remsely.psyhosom.api.controller

import arrow.core.flatMap
import arrow.core.raise.either
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.remsely.psyhosom.api.request.UpdateProfileRequest
import ru.remsely.psyhosom.api.response.ErrorResponse
import ru.remsely.psyhosom.api.response.toResponse
import ru.remsely.psyhosom.api.utils.AuthUserId
import ru.remsely.psyhosom.domain.account.dao.AccountFindingError
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.profile.dao.UserProfileFindingError
import ru.remsely.psyhosom.domain.profile.event.UpdateProfileEvent
import ru.remsely.psyhosom.domain.value_object.PhoneNumber
import ru.remsely.psyhosom.domain.value_object.PhoneNumberValidationError
import ru.remsely.psyhosom.domain.value_object.TelegramUsername
import ru.remsely.psyhosom.domain.value_object.TelegramUsernameValidationError
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.profile.FindProfileCommand
import ru.remsely.psyhosom.usecase.profile.ProfileUpdateError
import ru.remsely.psyhosom.usecase.profile.UpdateProfileCommand
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/patients/profile")
class ProfileController(
    private val updateProfileCommand: UpdateProfileCommand,
    private val findProfileCommand: FindProfileCommand
) {
    private val log = logger()

    @PutMapping
    fun updateUserProfile(@AuthUserId accountId: Long, @RequestBody request: UpdateProfileRequest): ResponseEntity<*> {
        log.info("PUT /api/v1/patients/profile | userId: $accountId.")
        return either {
            UpdateProfileEvent(
                accountId = accountId,
                firstName = request.firstName,
                lastName = request.lastName,
                phone = PhoneNumber(request.phone).bind(),
                telegram = TelegramUsername(request.telegram).bind()
            )
        }.flatMap {
            updateProfileCommand.execute(it)
        }.fold(
            { handleError(it) },
            {
                ResponseEntity
                    .ok()
                    .body(it.toResponse())
            }
        )
    }

    @GetMapping
    fun findUserProfile(@AuthUserId accountId: Long): ResponseEntity<*> {
        log.info("GET /api/v1/patients/profile | userId: $accountId.")
        return findProfileCommand.execute(accountId)
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
            is ProfileUpdateError.ProfileUsernameMustBeInContacts -> HttpStatus.BAD_REQUEST
            is AccountFindingError.NotFoundById -> HttpStatus.NOT_FOUND
            is UserProfileFindingError.NotFoundByUserId -> HttpStatus.NOT_FOUND
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
