package ru.remsely.psyhosom.domain.user

data class User(
    val id: Long,
    val username: String,
    val password: String,
    val role: Role
) {
    enum class Role {
        ADMIN, PATIENT, PSYCHOLOGIST
    }
}
