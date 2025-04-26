package ru.remsely.psyhosom.domain.account.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.account.Account
import ru.remsely.psyhosom.domain.error.DomainError

interface AccountCreator {
    fun createAccount(account: Account): Either<DomainError, Account>
}

sealed class AccountCreationValidationError(override val message: String) : DomainError.ValidationError {
    data class AlreadyExists(private val username: String) : AccountCreationValidationError(
        "User with username $username already exists."
    )
}
