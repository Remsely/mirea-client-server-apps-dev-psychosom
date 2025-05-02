package ru.remsely.psyhosom.domain.schedule.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError

interface ScheduleSlotEraser {
    fun removeOutdatedSlots(): Either<DomainError, Unit>
}
