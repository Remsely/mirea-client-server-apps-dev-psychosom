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
import ru.remsely.psyhosom.api.dto.request.CreateConsultationRequest
import ru.remsely.psyhosom.api.dto.response.ErrorResponse
import ru.remsely.psyhosom.api.dto.response.ConsultationResponse

@Tag(name = "Консультации", description = "Эндпоинты для оперирования консультациями")
interface ConsultationControllerContract {
    @Operation(summary = "Запись на консультацию")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Запись на консультацию прошла успешно",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ConsultationResponse::class)
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
    fun createConsultation(
        @Parameter(hidden = true)
        patientId: Long,

        @Parameter(description = "ID психолога, к которому записывается пациент")
        psychologistId: Long,

        @Schema(description = "Запрос на создание консультации")
        request: CreateConsultationRequest
    ): ResponseEntity<*>

    @Operation(summary = "Поиск активной консультации пациента у психолога")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Консультация найдена успешно",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ConsultationResponse::class)
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
                responseCode = "404",
                description = "Консультация не найдена",
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
    fun findActiveConsultation(
        @Parameter(hidden = true)
        patientId: Long,

        @Parameter(description = "ID психолога, к которому записывался пациент")
        psychologistId: Long
    ): ResponseEntity<*>

    @Operation(summary = "Завершение консультации")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Консультация завершена успешно",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = String::class)
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
    fun finishConsultation(
        @Parameter(hidden = true)
        patientId: Long,

        @Parameter(description = "ID психолога, к которому записывался пациент")
        psychologistId: Long,

        @Parameter(description = "ID консультации, которую нужно завершить")
        consultationId: Long
    ): ResponseEntity<*>
}
