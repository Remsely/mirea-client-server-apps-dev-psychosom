package ru.remsely.psyhosom.scheduled

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.consultation.NotifyComingConsultationsCommand

@Component
class ComingConsultationNotifier(
    private val command: NotifyComingConsultationsCommand
) {
    private val log = logger()

    @Scheduled(fixedRateString = "\${scheduled.consultations-notifier.fixed-rate-ms}")
    fun notifyComingConsultations() {
        log.info("Starting scheduled task to notify coming consultations...")
        command.execute()
            .fold(
                {
                    log.error("Consultations notification failed with error: ${it.message}.")
                },
                {
                    log.info("Successfully notified consultations.")
                }
            )
    }
}
