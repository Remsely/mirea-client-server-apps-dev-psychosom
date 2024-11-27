package ru.remsely.psyhosom.api.request

data class AuthRequest(
    val username: String,
    val password: String
) {
    override fun toString(): String {
        return "AuthRequest(login='$username')"
    }
}