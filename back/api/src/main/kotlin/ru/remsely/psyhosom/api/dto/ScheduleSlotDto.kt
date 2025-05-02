package ru.remsely.psyhosom.api.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalTime

@Schema(description = "Слот в расписании")
data class ScheduleSlotDto(
    @field:Schema(
        description = "Дата консультации",
        pattern = "dd-MM-yyyy",
        example = "25-12-2025"
    )
    val date: LocalDate,

    @field:Schema(
        description = "Время начала",
        pattern = "HH:mm:ss",
        example = "13:00:00"
    )
    val startTm: LocalTime,

    @field:Schema(
        description = "Время окончания",
        pattern = "HH:mm:ss",
        example = "14:00:00"
    )
    val endTm: LocalTime
)
