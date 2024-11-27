package ru.remsely.psyhosom.api.request

data class UpdateUserProfileRequest(
    val phone: String?,
    val telegram: String?,
    val firstName: String?,
    val lastName: String?
)
