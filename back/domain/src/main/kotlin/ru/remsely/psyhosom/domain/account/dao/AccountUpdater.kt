package ru.remsely.psyhosom.domain.account.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.account.Account
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.value_object.TelegramChatId

interface AccountUpdater {
    fun confirmAccount(id: Long, tgChatId: TelegramChatId): Either<DomainError, Account>
}

sealed class AccountUpdatingError(override val message: String) : DomainError.ValidationError {
    data class NotFoundById(private val id: Long) : AccountUpdatingError(
        "User with id $id not found."
    )
}
