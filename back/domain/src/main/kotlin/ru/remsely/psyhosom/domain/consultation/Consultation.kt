package ru.remsely.psyhosom.domain.consultation

import ru.remsely.psyhosom.domain.patient.Patient
import ru.remsely.psyhosom.domain.psychologist.Psychologist
import java.time.LocalDateTime

data class Consultation(
    val id: Long,
    val psychologist: Psychologist,
    val patient: Patient,
    val status: Status,
    val orderDate: LocalDateTime,
    val confirmationDate: LocalDateTime?,
    val startDate: LocalDateTime?
) {
    enum class Status {
        PENDING,
        CONFIRMED,
        CANCELED,
        FINISHED
    }
}
