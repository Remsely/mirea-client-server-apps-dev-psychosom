package ru.remsely.psyhosom.domain.account

data class Account(
    val id: Long? = null,
    val username: String,
    val password: String,
    val role: Role? = null
) {
    enum class Role {
        ADMIN, PATIENT, PSYCHOLOGIST
    }
}
