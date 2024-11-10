package ru.remsely.psyhosom.db.extensions

import ru.remsely.psyhosom.db.entity.User
import ru.remsely.psyhosom.domain.user.User as DomainUser

fun DomainUser.toEntity() = User(
    id = id,
    username = username,
    password = password,
    role = role
)
