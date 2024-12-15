package ru.remsely.psyhosom.usecase.auth

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.raise.either
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.remsely.psyhosom.domain.account.Account
import ru.remsely.psyhosom.domain.account.dao.AccountFinder
import ru.remsely.psyhosom.domain.account.dao.AccountUpdater
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.value_object.TelegramBotToken
import ru.remsely.psyhosom.domain.value_object.TelegramChatId

@Component
open class ConfirmAccountCommandImpl(
    private val accountFinder: AccountFinder,
    private val accountUpdater: AccountUpdater
) : ConfirmAccountCommand {
    @Transactional
    override fun execute(token: TelegramBotToken, chatId: TelegramChatId): Either<DomainError, Account> = either {
        accountFinder.findAccountByTgBotToken(token)
            .flatMap {
                accountUpdater.confirmAccount(it.id, chatId)
            }
            .bind()
    }
}
