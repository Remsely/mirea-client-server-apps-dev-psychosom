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
import ru.remsely.psyhosom.api.dto.request.CreateReviewRequest
import ru.remsely.psyhosom.api.dto.response.ErrorResponse
import ru.remsely.psyhosom.api.dto.response.ReviewResponse

@Tag(name = "Отзывы", description = "Эндпоинты для оперирования отзывами")
interface ReviewControllerContract {
    @Operation(summary = "Создание отзыва")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Отзыв создан успешно",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ReviewResponse::class)
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
                description = "Психолог не найден",
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
    fun createReview(
        @Parameter(hidden = true)
        patientId: Long,

        @Parameter(description = "ID психолога, которому добавляется отзыв")
        psychologistId: Long,

        request: CreateReviewRequest
    ): ResponseEntity<*>

    @Operation(summary = "Получение всех отзывов на психолога")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Отзывы найдены успешно",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ReviewResponse::class)
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
                description = "Психолог не найден",
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
    fun getPsychologistsReviews(
        @Parameter(description = "ID психолога, для которого ищутся отзывы")
        psychologistId: Long
    ): ResponseEntity<*>
}
