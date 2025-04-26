package ru.remsely.psyhosom.api.extensions.mapping

import org.springframework.http.HttpStatus
import ru.remsely.psyhosom.api.dto.response.ConsultationResponse
import ru.remsely.psyhosom.api.dto.response.ErrorResponse
import ru.remsely.psyhosom.api.dto.response.PatientResponse
import ru.remsely.psyhosom.api.dto.response.ReviewResponse
import ru.remsely.psyhosom.api.extensions.error_handling.responseStatus
import ru.remsely.psyhosom.domain.consultation.Consultation
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.Patient
import ru.remsely.psyhosom.domain.review.Review
import java.time.LocalDateTime

fun Patient.toDto() = PatientResponse(
    username = account.username,
    firstName = firstName,
    lastName = lastName
)

fun Review.toDto() = ReviewResponse(
    patient = ReviewResponse.Patient(
        id = patient.id,
        firstName = patient.firstName,
        lastName = patient.lastName
    ),
    psychologist = ReviewResponse.Psychologist(
        id = psychologist.id,
        firstName = psychologist.firstName,
        lastName = psychologist.lastName
    ),
    id = id,
    rating = rating.value,
    text = text,
    date = date
)

fun Consultation.toDto(): ConsultationResponse =
    ConsultationResponse(
        id = id,
        psychologistId = psychologist.id,
        patientId = patient.id,
        problemDescription = problemDescription,
        startDtTm = period.start,
        endDtTm = period.end,
        status = status,
        orderDtTm = orderDtTm,
        confirmationDtTm = confirmationDtTm
    )

fun DomainError.toDto() = ErrorResponse(
    status = this.responseStatus.name,
    message = this.message,
    source = this.javaClass.name,
    timestamp = LocalDateTime.now()
)

fun Exception.toDto(status: HttpStatus) = ErrorResponse(
    status = status.name,
    message = this.message ?: "No message provided.",
    source = this.javaClass.name,
    timestamp = LocalDateTime.now()
)
