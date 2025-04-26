package ru.remsely.psyhosom.domain.error

import arrow.core.Either
import arrow.core.getOrElse

sealed interface DomainError {
    val message: String

    interface ValidationError : DomainError
    interface AuthenticationError : DomainError
    interface MissingError : DomainError
    interface BusinessLogicError : DomainError
    interface IntegrationError : DomainError
}

class UnexpectedBehaviorException(override val message: String) : RuntimeException()

fun <R> Either<DomainError, R>.getOrThrowUnexpectedBehavior(): R =
    this.getOrElse {
        throw UnexpectedBehaviorException(it.message)
    }
