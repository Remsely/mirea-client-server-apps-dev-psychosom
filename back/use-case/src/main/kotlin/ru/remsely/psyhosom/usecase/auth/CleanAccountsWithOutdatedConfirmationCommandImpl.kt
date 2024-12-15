package ru.remsely.psyhosom.usecase.auth

import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.account.dao.AccountEraser
import ru.remsely.psyhosom.domain.account.dao.AccountFinder
import ru.remsely.psyhosom.monitoring.log.logger

@Component
class CleanAccountsWithOutdatedConfirmationCommandImpl(
    private val accountFinder: AccountFinder,
    private val accountEraser: AccountEraser,
    private val accountConfirmationNotifier: WebSocketAccountConfirmationNotifier
) : CleanAccountsWithOutdatedConfirmationCommand {
    private val log = logger()

    override fun execute() =
        accountFinder.findOutdatedAccounts()
            .let { accounts ->
                accountEraser.eraseAccountsByIds(
                    accounts.map { it.id }
                )
                accounts
            }
            .forEach { account ->
                accountConfirmationNotifier.sendNotification(
                    account.tgBotToken,
                    WebSocketAccountConfirmationNotifier.Status.OUTDATED
                )
            }.also {
                log.info("Outdated accounts successfully cleaned and notified.")
            }
}
