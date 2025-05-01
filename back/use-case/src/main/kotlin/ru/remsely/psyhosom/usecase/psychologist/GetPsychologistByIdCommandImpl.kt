package ru.remsely.psyhosom.usecase.psychologist

import arrow.core.Either
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.psychologist.Psychologist
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistFinder

@Component
class GetPsychologistByIdCommandImpl(
    private val psychologistFinder: PsychologistFinder
) : GetPsychologistByIdCommand {
    override fun execute(psychologistId: Long): Either<DomainError, Psychologist> =
        psychologistFinder.findPsychologistById(psychologistId)
}
