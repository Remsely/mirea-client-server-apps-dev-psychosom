package ru.remsely.psyhosom.db.dao

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.right
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.remsely.psyhosom.db.extensions.toDomain
import ru.remsely.psyhosom.db.repository.ScheduleSlotRepository
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.psychologist.Psychologist
import ru.remsely.psyhosom.domain.schedule.Schedule
import ru.remsely.psyhosom.domain.schedule.dao.ScheduleSlotEraser
import ru.remsely.psyhosom.domain.schedule.dao.ScheduleSlotFinder
import ru.remsely.psyhosom.monitoring.log.logger
import java.time.LocalDate
import java.time.LocalTime

@Component
open class ScheduleSlotDao(
    private val scheduleSlotRepository: ScheduleSlotRepository
) : ScheduleSlotFinder, ScheduleSlotEraser {
    private val log = logger()

    @Transactional(readOnly = true)
    override fun existsByDate(date: LocalDate): Boolean =
        scheduleSlotRepository.existsByDate(date)
            .also {
                log.info("Schedule slot exists in DB by date $date: $it.")
            }

    @Transactional(readOnly = true)
    override fun findAvailableSlotsByPsychologist(
        psychologist: Psychologist
    ): Either<DomainError, List<Schedule.Slot>> =
        scheduleSlotRepository.findByPsychologistIdAndAvailable(
            psychologistId = psychologist.id,
            available = true
        ).map { it.toDomain() }.also {
            log.info("Found ${it.size} available slots for psychologist ${psychologist.id} in DB.")
        }.right()

    @Transactional
    override fun removeOutdatedSlots(): Either<DomainError, Unit> = either {
        scheduleSlotRepository.deleteByEndDtTmIsBeforeAndAvailable(
            date = LocalDate.now(),
            time = LocalTime.now(),
            available = true,
        ).also {
            log.info("Removed $it outdated slots from DB.")
        }
    }
}
