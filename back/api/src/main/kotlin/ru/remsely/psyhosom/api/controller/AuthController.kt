package ru.remsely.psyhosom.api.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.remsely.psyhosom.api.request.AuthRequest
import ru.remsely.psyhosom.api.response.AuthResponse
import ru.remsely.psyhosom.api.response.ErrorResponse
import ru.remsely.psyhosom.domain.errors.DomainError
import ru.remsely.psyhosom.domain.extentions.logger
import ru.remsely.psyhosom.domain.user.User
import ru.remsely.psyhosom.domain.user.dao.UserCreationError
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
    fun registerAdmin(@Valid @RequestBody request: AuthRequest): ResponseEntity<*> {
        log.info("POST /auth/admin/register | AuthRequest: $request")
        return register(request, User.Role.ADMIN)
    }

    @PostMapping("/register/patient")
    fun registerPatient(@Valid @RequestBody request: AuthRequest): ResponseEntity<*> {
        log.info("POST /auth/patient/register | AuthRequest: $request")
        return register(request, User.Role.PATIENT)
    }

    @PostMapping("/register/psychologist")
    fun registerPsychologist(@Valid @RequestBody request: AuthRequest): ResponseEntity<*> {
        log.info("POST /auth/psychologist/register | AuthRequest: $request")
        return register(request, User.Role.PSYCHOLOGIST)
    }

    @PostMapping("/login")
    fun loginAdmin(@Valid @RequestBody request: AuthRequest): ResponseEntity<*> {
        log.info("POST /auth/login | AuthRequest: $request")
        return login(request)
    }

    private fun register(authRequest: AuthRequest, role: User.Role): ResponseEntity<*> =
        authService.registerUser(
            User(
                username = authRequest.username,
                password = authRequest.password,
                role = role
            )
        ).fold(
            { handleError(it) },
            {
                ResponseEntity
                    .ok()
                    .body(AuthResponse(it))
            }
        )

    private fun login(authRequest: AuthRequest): ResponseEntity<*> =
        authService.loginUser(
            User(
                username = authRequest.username,
                password = authRequest.password
            )
        ).fold(
            { handleError(it) },
            {
                ResponseEntity
                    .ok()
                    .body(AuthResponse(it))
            }
        )

    private fun handleError(error: DomainError): ResponseEntity<ErrorResponse> =
        when (error) {
            is UserCreationError.AlreadyExists -> HttpStatus.BAD_REQUEST
            is UserRegisterValidationError.InvalidUsername -> HttpStatus.BAD_REQUEST
            is UserLoginError.AuthenticationError -> HttpStatus.UNAUTHORIZED
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
