package ru.remsely.psyhosom.domain.account.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.account.Account
import ru.remsely.psyhosom.domain.error.DomainError

interface AccountFinder {
    fun findUserByUsername(username: String): Either<DomainError, Account>

    fun findUserById(id: Long): Either<DomainError, Account>
}

sealed class UserFindingError(override val message: String) : DomainError.ValidationError {
    data class NotFoundByUsername(private val username: String) : UserFindingError(
        "User with username $username not found."
    )

    data class NotFoundById(private val id: Long) : UserFindingError(
        "User with id $id not found."
    )
}
