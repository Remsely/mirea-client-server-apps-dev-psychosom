package ru.remsely.psyhosom.scheduled

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.psychologist.UpdatePsychologistScheduleSlotsCommand

@Component
class PsychologistScheduleSlotsUpdater(
    private val command: UpdatePsychologistScheduleSlotsCommand
) {
    private val log = logger()

    @EventListener(ApplicationReadyEvent::class)
    fun runOnceAfterStartup() {
        doUpdate()
    }

    @Scheduled(cron = "\${scheduled.psychologist-schedule-slots-addition.cron}")
    fun updateScheduleSlots() {
        doUpdate()
    }

    private fun doUpdate() {
        command.execute().fold(
            {
                log.error("Updating schedule slots failed.")
            },
            {
                log.info("Successfully updated psychologists schedule slots.")
            }
        )
    }
}
