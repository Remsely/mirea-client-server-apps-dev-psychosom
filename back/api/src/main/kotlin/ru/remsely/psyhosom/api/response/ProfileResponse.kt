package ru.remsely.psyhosom.api.response

import ru.remsely.psyhosom.domain.profile.Profile

data class ProfileResponse(
    val firstName: String?,
    val lastName: String?,
    val phone: String?,
    val telegram: String?
)

fun Profile.toResponse() = ProfileResponse(
    phone = phone?.value,
    telegram = telegram?.value,
    firstName = firstName,
    lastName = lastName
)
