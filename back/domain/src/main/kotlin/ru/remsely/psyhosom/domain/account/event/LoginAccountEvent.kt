package ru.remsely.psyhosom.domain.account.event

data class LoginAccountEvent(
    val username: String,
    val password: String
)
