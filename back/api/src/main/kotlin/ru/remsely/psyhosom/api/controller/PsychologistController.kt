package ru.remsely.psyhosom.api.controller

import arrow.core.flatMap
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.remsely.psyhosom.api.controller.open_api.PsychologistControllerContract
import ru.remsely.psyhosom.api.dto.ArticleBlockDto
import ru.remsely.psyhosom.api.dto.request.AddPsychologistEducationRequest
import ru.remsely.psyhosom.api.extensions.error_handling.toResponse
import ru.remsely.psyhosom.api.extensions.mapping.toDomain
import ru.remsely.psyhosom.api.extensions.mapping.toDto
import ru.remsely.psyhosom.api.extensions.validation.validate
import ru.remsely.psyhosom.api.utils.annotations.AuthPsychologistId
import ru.remsely.psyhosom.domain.psychologist.event.AddPsychologistEducationEvent
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.psychologist.AddPsychologistEducationCommand
import ru.remsely.psyhosom.usecase.psychologist.GetPsychologistByIdCommand
import ru.remsely.psyhosom.usecase.psychologist.PublishArticleCommand
import ru.remsely.psyhosom.usecase.schedule.GetPsychologistScheduleCommand

@RestController
@RequestMapping("/api/v1/psychologists")
class PsychologistController(
    private val publishArticleCommand: PublishArticleCommand,
    private val addEducationCommand: AddPsychologistEducationCommand,
    private val getPsychologistByIdCommand: GetPsychologistByIdCommand,
    private val getPsychologistScheduleCommand: GetPsychologistScheduleCommand
) : PsychologistControllerContract {
    private val log = logger()

    @PutMapping("/article")
    override fun publishArticle(
        @AuthPsychologistId psychologistId: Long,
        @RequestBody request: List<ArticleBlockDto>
    ): ResponseEntity<*> {
        log.info("POST /api/v1/psychologists/article | psychologistId: $psychologistId | request: $request")
        return publishArticleCommand.execute(
            psychologistId = psychologistId,
            article = request.toDomain()
        ).fold(
            { err ->
                err.toResponse()
                    .also { log.warn(err.message) }
            },
            {
                ResponseEntity.ok(it.toDto())
            }
        )
    }

    @PostMapping(
        "/education",
        consumes = ["multipart/form-data"],
        produces = ["application/json"]
    )
    override fun addEducation(
        @AuthPsychologistId psychologistId: Long,
        @ModelAttribute request: AddPsychologistEducationRequest
    ): ResponseEntity<*> {
        log.info("POST /api/v1/psychologists/education | psychologistId: $psychologistId | request: $request")
        return request.validate()
            .flatMap {
                addEducationCommand.execute(
                    psychologistId = psychologistId,
                    event = AddPsychologistEducationEvent(
                        files = request.files.map { it.toDomain() }
                    )
                )
            }
            .fold(
                { err ->
                    err.toResponse()
                        .also { log.warn(err.message) }
                },
                {
                    ResponseEntity.ok(it.toDto())
                }
            )
    }

    @GetMapping("/{psychologistId}")
    override fun getPsychologistInfo(
        @PathVariable psychologistId: Long
    ): ResponseEntity<*> {
        log.info("GET /api/v1/psychologists/$psychologistId")
        return getPsychologistByIdCommand.execute(psychologistId)
            .fold(
                { err ->
                    err.toResponse()
                        .also { log.warn(err.message) }
                },
                {
                    ResponseEntity.ok(it.toDto())
                }
            )
    }

    @GetMapping("/{psychologistId}/schedule")
    override fun getPsychologistSchedule(
        @PathVariable psychologistId: Long
    ): ResponseEntity<*> {
        log.info("GET /api/v1/psychologists/$psychologistId/schedule")
        return getPsychologistScheduleCommand.execute(psychologistId)
            .fold(
                { err ->
                    err.toResponse()
                        .also { log.warn(err.message) }
                },
                {
                    ResponseEntity.ok(it.toDto())
                }
            )
    }
}
