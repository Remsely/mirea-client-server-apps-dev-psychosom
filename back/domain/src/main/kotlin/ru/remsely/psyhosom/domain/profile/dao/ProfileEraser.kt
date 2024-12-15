package ru.remsely.psyhosom.domain.profile.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError

interface ProfileEraser {
    fun eraseProfilesByAccountIds(accountIds: List<Long>): Either<DomainError, Unit>
}
