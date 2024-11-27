package ru.remsely.psyhosom.db.extensions

import arrow.core.getOrElse
import ru.remsely.psyhosom.db.entity.User
import ru.remsely.psyhosom.db.entity.UserProfile
import ru.remsely.psyhosom.domain.value_object.PhoneNumber
import ru.remsely.psyhosom.domain.value_object.TelegramUsername
import ru.remsely.psyhosom.domain.user.User as DomainUser

fun User.toDomain() = DomainUser(
    id = id,
    username = username,
    password = password,
    role = role
)

fun UserProfile.toDomain() = DomainUser.Profile(
    id = id,
    user = user.toDomain(),
    phone = PhoneNumber(phone).getOrElse {
        throw RuntimeException("Invalid phone number.")
    },
    telegram = TelegramUsername(telegram).getOrElse {
        throw RuntimeException("Invalid telegram username.")
    },
    firstName = firstName,
    lastName = lastName,
)
