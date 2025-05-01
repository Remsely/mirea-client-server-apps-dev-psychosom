package ru.remsely.psyhosom.usecase.psychologist

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.psychologist.Psychologist

interface GetPsychologistByIdCommand {
    fun execute(psychologistId: Long): Either<DomainError, Psychologist>
}
