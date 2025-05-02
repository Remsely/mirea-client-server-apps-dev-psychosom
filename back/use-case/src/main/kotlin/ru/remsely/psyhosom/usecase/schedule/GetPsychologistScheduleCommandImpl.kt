package ru.remsely.psyhosom.usecase.schedule

import arrow.core.Either
import arrow.core.flatMap
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistFinder
import ru.remsely.psyhosom.domain.schedule.Schedule
import ru.remsely.psyhosom.domain.schedule.dao.ScheduleSlotFinder
import ru.remsely.psyhosom.monitoring.log.logger

@Component
class GetPsychologistScheduleCommandImpl(
    private val psychologistFinder: PsychologistFinder,
    private val scheduleSlotFinder: ScheduleSlotFinder
) : GetPsychologistScheduleCommand {
    private val log = logger()

    override fun execute(psychologistId: Long): Either<DomainError, Schedule> =
        psychologistFinder.findPsychologistById(psychologistId)
            .flatMap { psychologist ->
                scheduleSlotFinder.findAvailableSlotsByPsychologist(psychologist)
            }
            .map { slots ->
                Schedule(slots)
            }
            .also {
                log.info("Schedule for psychologist with id $psychologistId successfully found.")
            }
}
