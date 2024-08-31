package ru.remsely.psihosom.security.user

import ru.remsely.psihosom.domain.user.User as DomainUser

fun DomainUser.toDetails() = User(
    id = id,
    username = username,
    password = password,
    role = role
)
