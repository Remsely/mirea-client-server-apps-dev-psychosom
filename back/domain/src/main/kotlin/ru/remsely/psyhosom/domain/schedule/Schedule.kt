package ru.remsely.psyhosom.domain.schedule

import java.time.LocalDate
import java.time.LocalTime

@JvmInline
value class Schedule(
    val values: List<Slot>
) {
    data class Slot(
        val id: Long,
        val date: LocalDate,
        val startTm: LocalTime,
        val endTm: LocalTime,
        val available: Boolean
    )

    companion object {
        fun empty(): Schedule = Schedule(emptyList())
    }
}
