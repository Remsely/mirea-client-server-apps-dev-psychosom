package ru.remsely.psyhosom.domain.consultation

import ru.remsely.psyhosom.domain.patient.Patient
import ru.remsely.psyhosom.domain.psychologist.Psychologist
import ru.remsely.psyhosom.domain.schedule.Schedule
import ru.remsely.psyhosom.domain.value_object.MeetingLink
import java.time.LocalDateTime

data class Consultation(
    val id: Long,
    val psychologist: Psychologist,
    val patient: Patient,
    val problemDescription: String?,
    val scheduleSlot: Schedule.Slot,
    val status: Status,
    val orderDtTm: LocalDateTime,
    val confirmationDtTm: LocalDateTime?,
    val meetingLink: MeetingLink?,
) {
    enum class Status {
        PENDING,
        REJECTED,
        CONFIRMED,
        NOTIFIED,
        CANCELED,
        FINISHED
    }
}
