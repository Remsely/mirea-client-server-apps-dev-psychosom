package ru.remsely.psyhosom.domain.patient

import ru.remsely.psyhosom.domain.account.Account

data class Patient(
    val id: Long,
    val account: Account,
    val firstName: String,
    val lastName: String
)
