package ru.remsely.psyhosom.db.extensions

import ru.remsely.psyhosom.db.entity.User
import ru.remsely.psyhosom.domain.user.User as DomainUser

fun User.toDomain() = DomainUser(
    id = id,
    username = username,
    password = password,
    role = role
)
