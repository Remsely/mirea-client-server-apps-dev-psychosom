package ru.remsely.psyhosom.api.controller

import arrow.core.flatMap
import arrow.core.raise.either
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.remsely.psyhosom.api.controller.open_api.ReviewControllerContract
import ru.remsely.psyhosom.api.dto.request.CreateReviewRequest
import ru.remsely.psyhosom.api.extensions.error_handling.toResponse
import ru.remsely.psyhosom.api.extensions.mapping.toDto
import ru.remsely.psyhosom.api.utils.annotations.AuthPatientId
import ru.remsely.psyhosom.domain.review.event.CreateReviewEvent
import ru.remsely.psyhosom.domain.value_object.ReviewRating
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.review.CreateReviewCommand
import ru.remsely.psyhosom.usecase.review.FindPsychologistsReviewsCommand

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
            { err ->
                err.toResponse()
                    .also { log.warn(err.message) }
            },
            {
                ResponseEntity.ok(it.toDto())
            }
        )
    }

    @GetMapping("/{psychologistId}/reviews")
    override fun getPsychologistsReviews(@PathVariable psychologistId: Long): ResponseEntity<*> {
        log.info("GET /api/v1/reviews/$psychologistId.")
        return findPsychologistsReviewsCommand.execute(psychologistId)
            .fold(
                { err ->
                    err.toResponse()
                        .also { log.warn(err.message) }
                },
                { reviews ->
                    ResponseEntity.ok(
                        reviews.map { it.toDto() }
                    )
                }
            )
    }
}
