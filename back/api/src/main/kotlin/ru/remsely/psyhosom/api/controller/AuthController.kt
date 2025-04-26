package ru.remsely.psyhosom.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.remsely.psyhosom.api.controller.open_api.AuthControllerContract
import ru.remsely.psyhosom.api.dto.request.LoginRequest
import ru.remsely.psyhosom.api.dto.request.RegisterRequest
import ru.remsely.psyhosom.api.dto.response.LoginResponse
import ru.remsely.psyhosom.api.dto.response.RegisterResponse
import ru.remsely.psyhosom.api.extensions.error_handling.toResponse
import ru.remsely.psyhosom.domain.account.Account
import ru.remsely.psyhosom.domain.account.event.LoginAccountEvent
import ru.remsely.psyhosom.domain.account.event.RegisterAccountEvent
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.auth.AuthService

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) : AuthControllerContract {
    private val log = logger()

    @PostMapping("/register/admin") // TODO: Подумать, как защитить
    override fun registerAdmin(@RequestBody request: RegisterRequest): ResponseEntity<*> {
        log.info("POST /auth/admin/register | AuthRequest: $request")
        return register(request, Account.Role.ADMIN)
    }

    @PostMapping("/register/patient")
    override fun registerPatient(@RequestBody request: RegisterRequest): ResponseEntity<*> {
        log.info("POST /auth/patient/register | AuthRequest: $request")
        return register(request, Account.Role.PATIENT)
    }

    @PostMapping("/register/psychologist")
    override fun registerPsychologist(@RequestBody request: RegisterRequest): ResponseEntity<*> {
        log.info("POST /auth/psychologist/register | AuthRequest: $request")
        return register(request, Account.Role.PSYCHOLOGIST)
    }

    @PostMapping("/login")
    override fun loginAccount(@RequestBody request: LoginRequest): ResponseEntity<*> {
        log.info("POST /auth/login | AuthRequest: $request")
        return login(request)
    }

    private fun register(registerRequest: RegisterRequest, role: Account.Role): ResponseEntity<*> =
        authService.registerUser(
            RegisterAccountEvent(
                username = registerRequest.username,
                password = registerRequest.password,
                firstName = registerRequest.firstName,
                lastName = registerRequest.lastName,
                role = role
            )
        ).fold(
            { err ->
                err.toResponse()
                    .also { log.warn(err.message) }
            },
            {
                ResponseEntity
                    .ok()
                    .body(
                        RegisterResponse(
                            accountConfirmationUrl = it.confirmationUrl,
                            webSocketToken = it.webSocketToken.value
                        )
                    )
            }
        )

    private fun login(loginRequest: LoginRequest): ResponseEntity<*> =
        authService.loginUser(
            LoginAccountEvent(
                username = loginRequest.username,
                password = loginRequest.password
            )
        ).fold(
            { err ->
                err.toResponse()
                    .also { log.warn(err.message) }
            },
            {
                ResponseEntity
                    .ok()
                    .body(LoginResponse(it))
            }
        )
}
