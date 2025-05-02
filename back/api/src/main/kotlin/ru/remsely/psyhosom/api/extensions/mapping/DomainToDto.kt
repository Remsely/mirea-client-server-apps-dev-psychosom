package ru.remsely.psyhosom.api.extensions.mapping

import org.springframework.http.HttpStatus
import ru.remsely.psyhosom.api.dto.ArticleBlockDto
import ru.remsely.psyhosom.api.dto.ScheduleSlotDto
import ru.remsely.psyhosom.api.dto.response.ConsultationResponse
import ru.remsely.psyhosom.api.dto.response.ErrorResponse
import ru.remsely.psyhosom.api.dto.response.PatientResponse
import ru.remsely.psyhosom.api.dto.response.PsychologistFullInfoResponse
import ru.remsely.psyhosom.api.dto.response.PsychologistShortInfoResponse
import ru.remsely.psyhosom.api.dto.response.RegisterResponse
import ru.remsely.psyhosom.api.dto.response.ReviewResponse
import ru.remsely.psyhosom.api.dto.response.ScheduleItemResponse
import ru.remsely.psyhosom.api.extensions.error_handling.responseStatus
import ru.remsely.psyhosom.domain.consultation.Consultation
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.Patient
import ru.remsely.psyhosom.domain.psychologist.Article
import ru.remsely.psyhosom.domain.psychologist.Psychologist
import ru.remsely.psyhosom.domain.review.Review
import ru.remsely.psyhosom.domain.schedule.Schedule
import ru.remsely.psyhosom.usecase.auth.AccountCreatedEvent
import java.math.BigDecimal
import java.math.RoundingMode
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
    id = id,
    rating = rating.value,
    text = text,
    date = date
)

fun Consultation.toDto(): ConsultationResponse = ConsultationResponse(
    id = id,
    psychologistId = psychologist.id,
    patientId = patient.id,
    problemDescription = problemDescription,
    scheduleSlot = ScheduleSlotDto(
        date = scheduleSlot.date,
        startTm = scheduleSlot.startTm,
        endTm = scheduleSlot.endTm,
    ),
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

fun AccountCreatedEvent.toDto(): RegisterResponse = RegisterResponse(
    accountConfirmationUrl = confirmationUrl,
    webSocketToken = webSocketToken.value
)

fun Psychologist.toDto(): PsychologistFullInfoResponse = PsychologistFullInfoResponse(
    id = id,
    firstName = firstName,
    lastName = lastName,
    article = article.values
        .takeIf { it.isNotEmpty() }
        ?.map { it.toDto() },
    profileImage = profileImage,
    educationFiles = educations
        .takeIf { it.isNotEmpty() }
        ?.flatMap { edu -> edu.files.map { it.url } },
    rating = reviews.toAvgRating()
)

fun Article.ArticleBlock.toDto(): ArticleBlockDto = ArticleBlockDto(
    type = type,
    content = content
)

fun Schedule.toDto(): List<ScheduleItemResponse> = this.values
    .groupBy { it.date }
    .map { (date, slots) ->
        ScheduleItemResponse(
            date = date,
            slots = slots.sortedBy { it.startTm }
                .map {
                    ScheduleItemResponse.Slot(
                        startTm = it.startTm,
                        endTm = it.endTm
                    )
                }
        )
    }
    .sortedBy { it.date }

fun Psychologist.toShortDto(): PsychologistShortInfoResponse = PsychologistShortInfoResponse(
    id = id,
    firstName = firstName,
    lastName = lastName,
    profileImage = profileImage,
    rating = reviews.toAvgRating()
)

private fun List<Review>.toAvgRating(): Double? = this.takeIf { it.isNotEmpty() }?.let {
    BigDecimal(
        this.sumOf { it.rating.value }.toDouble() / this.size
    ).setScale(2, RoundingMode.HALF_UP).toDouble()
}
