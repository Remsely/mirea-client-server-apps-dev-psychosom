package ru.remsely.psyhosom.domain.account.event

import ru.remsely.psyhosom.domain.account.Account
import ru.remsely.psyhosom.domain.file.UploadedFile

data class RegisterPsychologistEvent(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val role: Account.Role,
    val profileImage: UploadedFile
)
