package ru.remsely.psyhosom.usecase.profile

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.profile.Profile

interface FindProfileCommand {
    fun execute(userId: Long): Either<DomainError, Profile>
}
