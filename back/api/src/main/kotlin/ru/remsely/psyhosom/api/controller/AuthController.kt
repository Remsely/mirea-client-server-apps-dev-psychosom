package ru.remsely.psyhosom.api.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.remsely.psyhosom.api.request.AuthRequest
import ru.remsely.psyhosom.api.response.ErrorResponse
import ru.remsely.psyhosom.api.response.LoginResponse
import ru.remsely.psyhosom.api.response.RegisterResponse
import ru.remsely.psyhosom.domain.account.Account
import ru.remsely.psyhosom.domain.account.dao.AccountCreationError
import ru.remsely.psyhosom.domain.account.event.LoginAccountEvent
import ru.remsely.psyhosom.domain.account.event.RegisterAccountEvent
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.profile.dao.UserProfileFindingError
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.auth.AuthService
import ru.remsely.psyhosom.usecase.auth.UserLoginError
import ru.remsely.psyhosom.usecase.auth.UserRegisterValidationError
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    private val log = logger()

    @PostMapping("/register/admin") // TODO: Подумать, как защитить
    fun registerAdmin(@RequestBody request: AuthRequest): ResponseEntity<*> {
        log.info("POST /auth/admin/register | AuthRequest: $request")
        return register(request, Account.Role.ADMIN)
    }

    @PostMapping("/register/patient")
    fun registerPatient(@RequestBody request: AuthRequest): ResponseEntity<*> {
        log.info("POST /auth/patient/register | AuthRequest: $request")
        return register(request, Account.Role.PATIENT)
    }

    @PostMapping("/register/psychologist")
    fun registerPsychologist(@RequestBody request: AuthRequest): ResponseEntity<*> {
        log.info("POST /auth/psychologist/register | AuthRequest: $request")
        return register(request, Account.Role.PSYCHOLOGIST)
    }

    @PostMapping("/login")
    fun loginAdmin(@RequestBody request: AuthRequest): ResponseEntity<*> {
        log.info("POST /auth/login | AuthRequest: $request")
        return login(request)
    }

    private fun register(authRequest: AuthRequest, role: Account.Role): ResponseEntity<*> =
        authService.registerUser(
            RegisterAccountEvent(
                username = authRequest.username,
                password = authRequest.password,
                role = role
            )
        ).fold(
            { handleError(it) },
            {
                ResponseEntity
                    .ok()
                    .body(
                        RegisterResponse(
                            tbBotConfirmationUrl = it,
                            webSocketToken = it.substring(it.indexOf("=") + 1) // TODO: Сделать нормально
                        )
                    )
            }
        )

    private fun login(authRequest: AuthRequest): ResponseEntity<*> =
        authService.loginUser(
            LoginAccountEvent(
                username = authRequest.username,
                password = authRequest.password
            )
        ).fold(
            { handleError(it) },
            {
                ResponseEntity
                    .ok()
                    .body(LoginResponse(it))
            }
        )

    private fun handleError(error: DomainError): ResponseEntity<ErrorResponse> =
        when (error) {
            is AccountCreationError.AlreadyExists -> HttpStatus.BAD_REQUEST
            is UserRegisterValidationError.InvalidUsername -> HttpStatus.BAD_REQUEST
            is UserProfileFindingError.ProfileWithUsernameAlreadyExists -> HttpStatus.BAD_REQUEST
            is UserLoginError.AuthenticationError -> HttpStatus.UNAUTHORIZED
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
