package ru.remsely.psyhosom.api.response

import ru.remsely.psyhosom.domain.consultation.Consultation
import java.time.LocalDateTime

data class FindActiveConsultationsResponse(
    val id: Long,
    val patientId: Long,
    val psychologistId: Long,
    val status: Consultation.Status,
    val orderDate: LocalDateTime,
    val confirmationDate: LocalDateTime?,
    val startDate: LocalDateTime?
)
