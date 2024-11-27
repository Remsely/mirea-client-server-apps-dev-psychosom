package ru.remsely.psyhosom.domain.user

import ru.remsely.psyhosom.domain.value_object.PhoneNumber
import ru.remsely.psyhosom.domain.value_object.TelegramUsername

data class User(
    val id: Long? = null,
    val username: String,
    val password: String,
    val role: Role? = null
) {
    enum class Role {
        ADMIN, PATIENT, PSYCHOLOGIST
    }

    data class Profile(
        val id: Long?,
        val user: User,
        val phone: PhoneNumber?,
        val telegram: TelegramUsername?,
        val firstName: String?,
        val lastName: String?,
    )
}
