package ru.remsely.psyhosom.api.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalTime

@Schema(description = "Элемент расписания психолога")
data class ScheduleItemResponse(
    @Schema(
        description = "Дата",
        pattern = "dd-MM-yyyy",
        example = "25-12-2025"
    )
    val date: LocalDate,

    @Schema(
        description = "Слоты, доступные в эту дату"
    )
    val slots: List<Slot>,
) {
    data class Slot(
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
        val endTm: LocalTime,
    )
}
