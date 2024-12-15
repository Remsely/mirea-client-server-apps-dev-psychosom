package ru.remsely.psyhosom.domain.account.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.account.Account
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.value_object.TelegramBotToken

interface AccountFinder {
    fun findAccountByUsername(username: String): Either<DomainError, Account>

    fun findAccountById(id: Long): Either<DomainError, Account>

    fun findAccountByTgBotToken(tgBotToken: TelegramBotToken): Either<DomainError, Account>

    fun findOutdatedAccounts(): List<Account>
}

sealed class AccountFindingError(override val message: String) : DomainError.ValidationError {
    data class NotFoundByUsername(private val username: String) : AccountFindingError(
        "User with username $username not found."
    )

    data class NotFoundById(private val id: Long) : AccountFindingError(
        "User with id $id not found."
    )

    data class NotFoundByTgBotToken(private val token: TelegramBotToken) : AccountFindingError(
        "User with token ${token.value} not found."
    )
}
