package ru.remsely.psyhosom.api.extensions.error_handling

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import ru.remsely.psyhosom.api.dto.response.ErrorResponse
import ru.remsely.psyhosom.api.extensions.mapping.toDto
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.error.UnexpectedBehaviorException

val DomainError.responseStatus: HttpStatus
    get() = when (this) {
        is DomainError.ValidationError -> HttpStatus.BAD_REQUEST
        is DomainError.AuthenticationError -> HttpStatus.UNAUTHORIZED
        is DomainError.MissingError -> HttpStatus.NOT_FOUND
        is DomainError.BusinessLogicError -> HttpStatus.CONFLICT
        is DomainError.IntegrationError -> HttpStatus.INTERNAL_SERVER_ERROR
    }

fun DomainError.toResponse(): ResponseEntity<ErrorResponse> =
    ResponseEntity
        .status(this.responseStatus)
        .body(this.toDto())

fun UnexpectedBehaviorException.toResponse(): ResponseEntity<ErrorResponse> =
    ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(this.toDto(HttpStatus.CONFLICT))

fun Exception.toResponse(): ResponseEntity<ErrorResponse> =
    ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(this.toDto(HttpStatus.INTERNAL_SERVER_ERROR))
