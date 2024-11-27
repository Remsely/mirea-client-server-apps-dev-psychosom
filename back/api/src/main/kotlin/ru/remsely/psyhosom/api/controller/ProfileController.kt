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
import ru.remsely.psyhosom.domain.account.dao.UserFindingError
import ru.remsely.psyhosom.domain.account.dao.UserProfileFindingError
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.profile.event.UpdateProfileEvent
import ru.remsely.psyhosom.domain.value_object.PhoneNumber
import ru.remsely.psyhosom.domain.value_object.PhoneNumberCreationError
import ru.remsely.psyhosom.domain.value_object.TelegramUsername
import ru.remsely.psyhosom.domain.value_object.TelegramUsernameCreationError
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.user.ProfileManager
import ru.remsely.psyhosom.usecase.user.UserProfileManagingError
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/profiles")
class ProfileController(
    private val profileManager: ProfileManager
) {
    private val log = logger()

    @PutMapping
    fun updateUserProfile(@AuthUserId userId: Long, @RequestBody request: UpdateProfileRequest): ResponseEntity<*> {
        log.info("PUT /api/v1/users/profile | userId: $userId.")
        return either {
            UpdateProfileEvent(
                userId = userId,
                firstName = request.firstName,
                lastName = request.lastName,
                phone = PhoneNumber(request.phone).bind(),
                telegram = TelegramUsername(request.telegram).bind()
            )
        }.flatMap {
            profileManager.createOrUpdateProfile(it)
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
    fun findUserProfile(@AuthUserId userId: Long): ResponseEntity<*> {
        log.info("GET /api/v1/users/profile | userId: $userId.")
        return profileManager.findProfileByUserId(userId)
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
            is PhoneNumberCreationError.InvalidPhoneNumber -> HttpStatus.BAD_REQUEST
            is TelegramUsernameCreationError.InvalidTelegramUsername -> HttpStatus.BAD_REQUEST
            is UserProfileManagingError.ProfileUsernameMustBeInContacts -> HttpStatus.BAD_REQUEST
            is UserFindingError.NotFoundById -> HttpStatus.NOT_FOUND
            is UserProfileFindingError.NotFoundByUserId -> HttpStatus.NOT_FOUND
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }.let {
            ResponseEntity
                .status(it)
                .body(
                    ErrorResponse(
                        message = error.message,
                        timestamp = LocalDateTime.now(),
                        status = it.name
                    )
                ).also {
                    log.warn(error.message)
                }
        }
}
