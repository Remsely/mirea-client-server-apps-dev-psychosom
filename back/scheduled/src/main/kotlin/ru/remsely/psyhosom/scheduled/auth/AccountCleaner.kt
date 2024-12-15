package ru.remsely.psyhosom.scheduled.auth

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.auth.CleanAccountsWithOutdatedConfirmationCommand

const val ACCOUNT_CLEANER_FIXED_RATE = 150000L

@Component
class AccountCleaner(
    private val command: CleanAccountsWithOutdatedConfirmationCommand
) {
    private val log = logger()

    @Scheduled(fixedRate = ACCOUNT_CLEANER_FIXED_RATE)
    fun cleanAccountsWithExpiredAuthConfirmation() {
        log.info("Starting scheduled task to clean accounts with expired auth confirmation...")
        command.execute()
    }
}
