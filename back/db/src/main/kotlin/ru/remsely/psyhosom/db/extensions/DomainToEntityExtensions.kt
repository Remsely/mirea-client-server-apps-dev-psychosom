package ru.remsely.psyhosom.db.extensions

import ru.remsely.psyhosom.db.entity.Account
import ru.remsely.psyhosom.db.entity.Profile
import ru.remsely.psyhosom.domain.account.Account as DomainAccount
import ru.remsely.psyhosom.domain.profile.Profile as DomainProfile

fun DomainAccount.toEntity() = Account(
    id = id,
    username = username,
    password = password,
    role = role,
    isConfirmed = isConfirmed,
    tgBotToken = tgBotToken.value,
    tgChatId = tgChatId.value,
    registrationDate = registrationDate
)

fun DomainProfile.toEntity() = Profile(
    id = id,
    account = account.toEntity(),
    phone = phone?.value,
    telegram = telegram?.value,
    firstName = firstName,
    lastName = lastName,
)
