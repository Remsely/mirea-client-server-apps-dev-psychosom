package ru.remsely.psyhosom.security.user

import ru.remsely.psyhosom.domain.account.Account

fun Account.toDetails() = User(
    id = id!!,
    username = username,
    password = password,
    role = role!!
)
