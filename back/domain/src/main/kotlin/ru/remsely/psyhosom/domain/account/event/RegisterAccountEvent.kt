package ru.remsely.psyhosom.domain.account.event

import ru.remsely.psyhosom.domain.account.Account

data class RegisterAccountEvent(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val role: Account.Role
)
