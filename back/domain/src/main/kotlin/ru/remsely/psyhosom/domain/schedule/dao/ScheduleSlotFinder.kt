package ru.remsely.psyhosom.domain.schedule.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.psychologist.Psychologist
import ru.remsely.psyhosom.domain.schedule.Schedule
import java.time.LocalDate

interface ScheduleSlotFinder {
    fun existsByDate(date: LocalDate): Boolean

    fun findAvailableSlotsByPsychologist(psychologist: Psychologist): Either<DomainError, List<Schedule.Slot>>
}
