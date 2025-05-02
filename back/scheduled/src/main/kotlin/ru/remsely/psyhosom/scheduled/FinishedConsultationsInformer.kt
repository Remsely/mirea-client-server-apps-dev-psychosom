package ru.remsely.psyhosom.scheduled

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.consultation.InformFinishedConsultationsCommand

@Component
class FinishedConsultationsInformer(
    private val command: InformFinishedConsultationsCommand
) {
    private val log = logger()

    @Scheduled(fixedRateString = "\${scheduled.consultations-notifier.fixed-rate-ms}")
    fun notifyComingConsultations() {
        log.info("Starting scheduled task to inform finished consultations...")
        command.execute()
            .fold(
                {
                    log.error("Consultations finishing informing failed with error: ${it.message}.")
                },
                {
                    log.info("Successfully informed finished consultations.")
                }
            )
    }
}
