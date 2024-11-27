package ru.remsely.psyhosom.domain.errors

sealed interface DomainError {
    val message: String

    interface BusinessLogicError : DomainError
    interface ValidationError : DomainError
    interface IntegrationError : DomainError
}
