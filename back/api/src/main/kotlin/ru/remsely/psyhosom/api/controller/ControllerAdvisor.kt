package ru.remsely.psyhosom.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.remsely.psyhosom.api.dto.response.ErrorResponse
import ru.remsely.psyhosom.api.extensions.error_handling.toResponse
import ru.remsely.psyhosom.domain.error.UnexpectedBehaviorException

@RestControllerAdvice
class ControllerAdvisor {
    @ExceptionHandler(UnexpectedBehaviorException::class)
    fun handleUnexpectedBehavior(ex: UnexpectedBehaviorException): ResponseEntity<ErrorResponse> = ex.toResponse()

    @ExceptionHandler(Exception::class)
    fun handleUnexpectedBehavior(ex: Exception): ResponseEntity<ErrorResponse> = ex.toResponse()
}
