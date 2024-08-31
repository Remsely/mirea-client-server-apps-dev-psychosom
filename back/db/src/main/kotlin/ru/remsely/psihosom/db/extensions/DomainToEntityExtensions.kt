package ru.remsely.psihosom.db.extensions

import ru.remsely.psihosom.db.entity.User
import ru.remsely.psihosom.domain.user.User as DomainUser

fun DomainUser.toEntity() = User(
    id = id,
    username = username,
    password = password,
    role = role
)
