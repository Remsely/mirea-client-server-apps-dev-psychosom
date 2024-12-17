package ru.remsely.psyhosom.domain.psychologist.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.psychologist.Psychologist

interface PsychologistCreator {
    fun createPsychologist(psychologist: Psychologist): Either<DomainError, Psychologist>
}
