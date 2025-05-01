package ru.remsely.psyhosom.usecase.psychologist

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.psychologist.Psychologist
import ru.remsely.psyhosom.domain.psychologist.event.AddPsychologistEducationEvent

interface AddPsychologistEducationCommand {
    fun execute(psychologistId: Long, event: AddPsychologistEducationEvent): Either<DomainError, Psychologist>
}
