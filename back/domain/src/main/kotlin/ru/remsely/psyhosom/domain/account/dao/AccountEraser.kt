package ru.remsely.psyhosom.domain.account.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError

interface AccountEraser {
    fun eraseAccountsByIds(ids: List<Long>): Either<DomainError, Unit>
}
