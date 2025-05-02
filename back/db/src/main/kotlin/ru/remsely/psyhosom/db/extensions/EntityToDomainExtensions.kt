package ru.remsely.psyhosom.db.extensions

import ru.remsely.psyhosom.db.entity.Account
import ru.remsely.psyhosom.db.entity.Consultation
import ru.remsely.psyhosom.db.entity.Patient
import ru.remsely.psyhosom.db.entity.Psychologist
import ru.remsely.psyhosom.db.entity.PsychologistEducation
import ru.remsely.psyhosom.db.entity.PsychologistEducationFile
import ru.remsely.psyhosom.db.entity.Review
import ru.remsely.psyhosom.db.entity.ScheduleSlot
import ru.remsely.psyhosom.domain.error.getOrThrowUnexpectedBehavior
import ru.remsely.psyhosom.domain.value_object.MeetingLink
import ru.remsely.psyhosom.domain.value_object.ReviewRating
import ru.remsely.psyhosom.domain.value_object.TelegramBotToken
import ru.remsely.psyhosom.domain.value_object.TelegramChatId
import ru.remsely.psyhosom.domain.account.Account as DomainAccount
import ru.remsely.psyhosom.domain.consultation.Consultation as DomainConsultation
import ru.remsely.psyhosom.domain.patient.Patient as DomainPatient
import ru.remsely.psyhosom.domain.psychologist.Article as DomainArticle
import ru.remsely.psyhosom.domain.psychologist.Psychologist as DomainPsychologist
import ru.remsely.psyhosom.domain.review.Review as DomainReview
import ru.remsely.psyhosom.domain.schedule.Schedule as DomainSchedule

fun Account.toDomain() = DomainAccount(
    id = id,
    username = username,
    password = password,
    role = role,
    isConfirmed = isConfirmed,
    tgBotToken = TelegramBotToken(tgBotToken).getOrThrowUnexpectedBehavior(),
    tgChatId = TelegramChatId(tgChatId).getOrThrowUnexpectedBehavior(),
    registrationDate = registrationDtTm
)

fun Patient.toDomain() = DomainPatient(
    id = id,
    account = account.toDomain(),
    firstName = firstName,
    lastName = lastName
)

fun Psychologist.toDomain() = DomainPsychologist(
    id = id,
    account = account.toDomain(),
    firstName = firstName,
    lastName = lastName,
    profileImage = profileImageUrl,
    article = article.toDomain(),
    schedule = DomainSchedule(scheduleSlots.map { it.toDomain() }),
    educations = educations.map { it.toDomain() },
    reviews = reviews.map { it.toDomain() }
)

fun ScheduleSlot.toDomain() = DomainSchedule.Slot(
    id = id,
    date = date,
    startTm = startTm,
    endTm = endTm,
    available = available
)

fun PsychologistEducation.toDomain() = DomainPsychologist.Education(
    id = id,
    files = files.map { it.toDomain() }
)

fun PsychologistEducationFile.toDomain() = DomainPsychologist.Education.File(
    id = id,
    url = fileUrl,
)

fun Psychologist.Article.toDomain() = DomainArticle(
    values = values.map { it.toDomain() }
)

fun Psychologist.Article.ArticleBlock.toDomain() = DomainArticle.ArticleBlock(
    type = type,
    content = content
)

fun Consultation.toDomain() = DomainConsultation(
    id = id,
    psychologist = psychologist.toDomain(),
    patient = patient.toDomain(),
    problemDescription = problemDescription,
    scheduleSlot = scheduleSlot.toDomain(),
    status = status,
    orderDtTm = orderDtTm,
    confirmationDtTm = confirmationDtTm,
    meetingLink = meetingLink?.let {
        MeetingLink(it).getOrThrowUnexpectedBehavior()
    }
)

fun Review.toDomain() = DomainReview(
    id = id,
    patient = patient.toDomain(),
    rating = ReviewRating(rating).getOrThrowUnexpectedBehavior(),
    text = text,
    date = date
)
