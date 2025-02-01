package ru.remsely.psyhosom.api.controller

import arrow.core.flatMap
import arrow.core.raise.either
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.remsely.psyhosom.api.controller.open_api.ReviewControllerContract
import ru.remsely.psyhosom.api.request.CreateReviewRequest
import ru.remsely.psyhosom.api.response.ErrorResponse
import ru.remsely.psyhosom.api.response.toResponse
import ru.remsely.psyhosom.api.utils.annotation.AuthPatientId
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.dao.PatientFindingError
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistFindingError
import ru.remsely.psyhosom.domain.review.event.CreateReviewEvent
import ru.remsely.psyhosom.domain.value_object.ReviewRating
import ru.remsely.psyhosom.domain.value_object.ReviewRatingValidationError
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.review.CreateReviewCommand
import ru.remsely.psyhosom.usecase.review.FindPsychologistsReviewsCommand
import ru.remsely.psyhosom.usecase.review.ReviewCreationError
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/psychologists")
class ReviewController(
    private val createReviewCommand: CreateReviewCommand,
    private val findPsychologistsReviewsCommand: FindPsychologistsReviewsCommand
) : ReviewControllerContract {
    private val log = logger()

    @PostMapping("/{psychologistId}/reviews")
    override fun createReview(
        @AuthPatientId patientId: Long,
        @PathVariable psychologistId: Long,
        @RequestBody request: CreateReviewRequest
    ): ResponseEntity<*> {
        log.info("POST /api/v1/reviews | request: $request")
        return either {
            CreateReviewEvent(
                patientId = patientId,
                psychologistId = psychologistId,
                rating = ReviewRating(request.rating).bind(),
                text = request.text
            )
        }.flatMap {
            createReviewCommand.execute(it)
        }.fold(
            { handleError(it) },
            {
                ResponseEntity
                    .ok()
                    .body(
                        it.toResponse()
                    )
            }
        )
    }

    @GetMapping("/{psychologistId}/reviews")
    override fun getPsychologistsReviews(@PathVariable psychologistId: Long): ResponseEntity<*> {
        log.info("GET /api/v1/reviews/$psychologistId.")
        return findPsychologistsReviewsCommand.execute(psychologistId)
            .fold(
                { handleError(it) },
                { reviews ->
                    ResponseEntity
                        .ok()
                        .body(
                            reviews.map { it.toResponse() }
                        )
                }
            )
    }

    private fun handleError(error: DomainError): ResponseEntity<ErrorResponse> =
        when (error) {
            is PatientFindingError.NotFoundById -> HttpStatus.BAD_REQUEST
            is ReviewCreationError.ReviewForPsychologistAlreadyExists -> HttpStatus.BAD_REQUEST
            is ReviewCreationError.FinishedConsultationWithPsychologistNotFound -> HttpStatus.BAD_REQUEST
            is ReviewRatingValidationError.InvalidReviewRation -> HttpStatus.BAD_REQUEST
            is PsychologistFindingError.NotFoundById -> HttpStatus.NOT_FOUND
            is ReviewCreationError.PatientPersonalDataNotFilled -> HttpStatus.CONFLICT
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
