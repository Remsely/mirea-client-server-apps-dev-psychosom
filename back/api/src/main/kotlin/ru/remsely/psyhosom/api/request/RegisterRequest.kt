package ru.remsely.psyhosom.api.request

data class RegisterRequest(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String
) {
    override fun toString(): String {
        return "AuthRequest(login='$username')"
    }
}
