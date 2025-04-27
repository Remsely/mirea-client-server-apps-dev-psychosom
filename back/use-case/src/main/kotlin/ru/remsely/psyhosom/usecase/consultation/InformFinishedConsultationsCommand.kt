package ru.remsely.psyhosom.usecase.consultation

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError

interface InformFinishedConsultationsCommand {
    fun execute(): Either<DomainError, Unit>
}
