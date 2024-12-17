package ru.remsely.psyhosom.domain.psychologist.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.psychologist.Psychologist

interface PsychologistFinder {
    fun findPsychologistById(id: Long): Either<DomainError, Psychologist>
}

sealed class PsychologistFindingError(override val message: String) : DomainError.ValidationError {
    data class NotFoundById(private val userId: Long) : PsychologistFindingError(
        "Psychologist with id $userId not found."
    )
}
