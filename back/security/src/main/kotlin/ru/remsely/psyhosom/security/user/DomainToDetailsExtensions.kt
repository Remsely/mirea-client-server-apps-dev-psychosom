package ru.remsely.psyhosom.security.user

import ru.remsely.psyhosom.domain.user.User as DomainUser

fun DomainUser.toDetails() = User(
    id = id!!,
    username = username,
    password = password,
    role = role!!
)
