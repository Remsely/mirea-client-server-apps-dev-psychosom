package ru.remsely.psyhosom.usecase.psychologist

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError

interface UpdatePsychologistScheduleSlotsCommand {
    fun execute(): Either<DomainError, Unit>
}
