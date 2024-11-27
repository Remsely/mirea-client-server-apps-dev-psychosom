package ru.remsely.psyhosom.db.extensions

import ru.remsely.psyhosom.db.entity.User
import ru.remsely.psyhosom.db.entity.UserProfile
import ru.remsely.psyhosom.domain.user.User as DomainUser

fun DomainUser.toEntity() = User(
    id = id,
    username = username,
    password = password,
    role = role!!
)

fun DomainUser.Profile.toEntity() = UserProfile(
    id = id,
    user = user.toEntity(),
    phone = phone?.value,
    telegram = telegram?.value,
    firstName = firstName,
    lastName = lastName,
)
