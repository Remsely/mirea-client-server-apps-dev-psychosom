package ru.remsely.psyhosom.usecase.psychologist

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistFinder
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistUpdater
import ru.remsely.psyhosom.domain.schedule.Schedule
import ru.remsely.psyhosom.domain.schedule.dao.ScheduleSlotEraser
import ru.remsely.psyhosom.domain.schedule.dao.ScheduleSlotFinder
import java.time.LocalDate
import java.time.LocalTime
import java.util.concurrent.ThreadLocalRandom

@Component
class UpdatePsychologistScheduleSlotsCommandImpl(
    private val psychologistFinder: PsychologistFinder,
    private val psychologistUpdater: PsychologistUpdater,
    private val scheduleSlotFinder: ScheduleSlotFinder,
    private val scheduleSlotEraser: ScheduleSlotEraser
) : UpdatePsychologistScheduleSlotsCommand {
    override fun execute(): Either<DomainError, Unit> =
        scheduleSlotEraser.removeOutdatedSlots()
            .flatMap {
                psychologistFinder.findAllPsychologists()
            }
            .map {
                LocalDate.now() to it
            }
            .flatMap { (today, psychologists) ->
                val daysToAddSlots = (0L..6L)
                    .map {
                        today.plusDays(it)
                    }
                    .filter {
                        !scheduleSlotFinder.existsByDate(it)
                    }

                psychologists.forEach { psychologists ->
                    var updatedSlots = psychologists.schedule.values

                    daysToAddSlots.forEach { date ->
                        updatedSlots = updatedSlots + generateRandomSlots(date)
                    }

                    psychologistUpdater.updatePsychologist(
                        psychologists.copy(
                            schedule = Schedule(updatedSlots)
                        )
                    ).mapLeft { return it.left() }
                }
                Unit.right()
            }

    private fun generateRandomSlots(date: LocalDate): List<Schedule.Slot> {
        val rng = ThreadLocalRandom.current()
        val hours = (9..21).shuffled()
        val count = rng.nextInt(3, 6)

        return hours.take(count).map { hour ->
            val start = LocalTime.of(hour, 0)
            val end = start.plusHours(1)
            Schedule.Slot(
                id = 0L,
                date = date,
                startTm = start,
                endTm = end,
                available = true
            )
        }
    }
}
