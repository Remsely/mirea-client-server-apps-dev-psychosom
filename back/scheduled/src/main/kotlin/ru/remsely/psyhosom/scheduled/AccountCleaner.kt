package ru.remsely.psyhosom.scheduled

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.auth.CleanAccountsWithOutdatedConfirmationCommand

@Component
class AccountCleaner(
    private val command: CleanAccountsWithOutdatedConfirmationCommand
) {
    private val log = logger()

    @Scheduled(fixedRateString = "\${scheduled.account-cleaner.fixed-rate-ms}")
    fun cleanAccountsWithExpiredAuthConfirmation() {
        log.info("Starting scheduled task to clean accounts with expired auth confirmation...")
        command.execute()
    }
}
