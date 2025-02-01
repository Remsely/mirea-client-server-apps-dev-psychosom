package ru.remsely.psyhosom.api.controller.open_api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import ru.remsely.psyhosom.api.request.UpdatePatientRequest
import ru.remsely.psyhosom.api.response.ErrorResponse
import ru.remsely.psyhosom.api.response.PatientResponse
import ru.remsely.psyhosom.domain.patient.event.UpdatePatientEvent

@Tag(name = "Пациенты", description = "Эндпоинты для оперирования данными пациентов")
interface PatientControllerContract {
    @Operation(summary = "Обновление профиля пациента")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Профиль обновлен успешно",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = UpdatePatientEvent::class)
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
    @SecurityRequirement(name = "bearerAuth")
    fun updatePatient(
        @Parameter(hidden = true)
        accountId: Long,

        request: UpdatePatientRequest
    ): ResponseEntity<*>

    @Operation(summary = "Получение профиля пациента")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Профиль найден успешно",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = PatientResponse::class)
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
    @SecurityRequirement(name = "bearerAuth")
    fun findPatient(
        @Parameter(hidden = true)
        accountId: Long
    ): ResponseEntity<*>
}
