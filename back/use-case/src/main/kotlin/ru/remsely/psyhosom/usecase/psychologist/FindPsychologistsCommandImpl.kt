package ru.remsely.psyhosom.usecase.psychologist

import arrow.core.Either
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.psychologist.Psychologist
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistFinder
import ru.remsely.psyhosom.monitoring.log.logger

@Component
open class FindPsychologistsCommandImpl(
    private val psychologistFinder: PsychologistFinder
) : FindPsychologistsCommand {
    private val log = logger()

    override fun execute(): Either<DomainError, List<Psychologist>> =
        psychologistFinder.findAllPsychologists()
            .also {
                log.info("Psychologists successfully found.")
            }
}
