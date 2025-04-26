package ru.remsely.psyhosom.usecase.auth

import arrow.core.Either
import ru.remsely.psyhosom.domain.account.Account
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.value_object.TelegramBotToken
import ru.remsely.psyhosom.domain.value_object.TelegramChatId

interface ConfirmAccountCommand {
    fun execute(token: TelegramBotToken, chatId: TelegramChatId): Either<DomainError, Account>
}
