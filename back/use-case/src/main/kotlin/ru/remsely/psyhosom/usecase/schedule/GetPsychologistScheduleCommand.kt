package ru.remsely.psyhosom.usecase.schedule

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.schedule.Schedule

interface GetPsychologistScheduleCommand {
    fun execute(psychologistId: Long): Either<DomainError, Schedule>
}
