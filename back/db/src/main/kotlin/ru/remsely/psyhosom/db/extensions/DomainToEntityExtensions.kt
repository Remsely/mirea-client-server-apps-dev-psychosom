package ru.remsely.psyhosom.db.extensions

import ru.remsely.psyhosom.db.entity.Account
import ru.remsely.psyhosom.db.entity.Consultation
import ru.remsely.psyhosom.db.entity.Patient
import ru.remsely.psyhosom.db.entity.Psychologist
import ru.remsely.psyhosom.db.entity.Review
import ru.remsely.psyhosom.domain.account.Account as DomainAccount
import ru.remsely.psyhosom.domain.consultation.Consultation as DomainConsultation
import ru.remsely.psyhosom.domain.patient.Patient as DomainPatient
import ru.remsely.psyhosom.domain.psychologist.Psychologist as DomainPsychologist
import ru.remsely.psyhosom.domain.review.Review as DomainReview

fun DomainAccount.toEntity() = Account(
    id = id,
    username = username,
    password = password,
    role = role,
    isConfirmed = isConfirmed,
    tgBotToken = tgBotToken.value,
    tgChatId = tgChatId.value,
    registrationDtTm = registrationDate
)

fun DomainPatient.toEntity() = Patient(
    id = id,
    account = account.toEntity(),
    firstName = firstName,
    lastName = lastName
)

fun DomainPsychologist.toEntity() = Psychologist(
    id = id,
    account = account.toEntity(),
    firstName = firstName,
    lastName = lastName
)

fun DomainConsultation.toEntity() = Consultation(
    id = id,
    psychologist = psychologist.toEntity(),
    patient = patient.toEntity(),
    status = status,
    problemDescription = problemDescription,
    startDtTm = period.start,
    endDtTm = period.end,
    orderDtTm = orderDtTm,
    confirmationDtTm = confirmationDtTm,
    meetingLink = meetingLink?.value
)

fun DomainReview.toEntity() = Review(
    id = id,
    psychologist = psychologist.toEntity(),
    patient = patient.toEntity(),
    rating = rating.value,
    text = text,
    date = date
)
