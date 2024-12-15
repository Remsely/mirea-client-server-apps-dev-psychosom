package ru.remsely.psyhosom.domain.account.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.account.Account
import ru.remsely.psyhosom.domain.error.DomainError

interface AccountCreator {
    fun createUser(account: Account): Either<DomainError, Account>
}

sealed class AccountCreationError(override val message: String) : DomainError.BusinessLogicError {
    data class AlreadyExists(private val username: String) : AccountCreationError(
        "User with username $username already exists."
    )
}
