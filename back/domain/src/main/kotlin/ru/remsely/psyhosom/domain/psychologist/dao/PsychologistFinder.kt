package ru.remsely.psyhosom.domain.psychologist.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.psychologist.Psychologist

interface PsychologistFinder {
    fun findPsychologistById(id: Long): Either<DomainError, Psychologist>

    fun findPsychologistByAccountId(accountId: Long): Either<DomainError, Psychologist>
}

sealed class PsychologistMissingError(override val message: String) : DomainError.MissingError {
    data class NotFoundById(private val userId: Long) : PsychologistMissingError(
        "Psychologist with id $userId not found."
    )

    data class NotFoundByAccountId(private val accountId: Long) : PsychologistMissingError(
        "Psychologist with for account $accountId not found."
    )
}
