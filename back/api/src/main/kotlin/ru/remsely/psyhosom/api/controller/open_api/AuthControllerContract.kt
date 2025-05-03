package ru.remsely.psyhosom.api.controller.open_api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import ru.remsely.psyhosom.api.request.LoginRequest
import ru.remsely.psyhosom.api.request.RegisterRequest
import ru.remsely.psyhosom.api.response.ErrorResponse
import ru.remsely.psyhosom.api.response.LoginResponse
import ru.remsely.psyhosom.api.response.RegisterResponse

@Tag(name = "Авторизация", description = "Эндпоинты для авторизации пользователей")
interface AuthControllerContract {
    @Operation(summary = "Регистрация нового админа")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Регистрация прошла успешно",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = RegisterResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные входные данные",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class)
                    )
                ]
            )
        ]
    )
    fun registerAdmin(
        request: RegisterRequest
    ): ResponseEntity<*>

    @Operation(summary = "Регистрация нового пациента")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Регистрация прошла успешно",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = RegisterResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные входные данные",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class)
                    )
                ]
            )
        ]
    )
    fun registerPatient(
        request: RegisterRequest
    ): ResponseEntity<*>

    @Operation(summary = "Регистрация нового психолога")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Регистрация прошла успешно",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = RegisterResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные входные данные",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class)
                    )
                ]
            )
        ]
    )
    fun registerPsychologist(
        request: RegisterRequest
    ): ResponseEntity<*>

    @Operation(summary = "Авторизация пользователей")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Авторизация прошла успешно",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = LoginResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные входные данные",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Неверные учетные данные",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class)
                    )
                ]
            )
        ]
    )
    fun loginAccount(
        request: LoginRequest
    ): ResponseEntity<*>
}
