package ru.remsely.psyhosom.api.response

import ru.remsely.psyhosom.domain.user.User

data class UserProfileResponse(
    val firstName: String?,
    val lastName: String?,
    val phone: String?,
    val telegram: String?
)

fun User.Profile.toResponse() = UserProfileResponse(
    phone = phone?.value,
    telegram = telegram?.value,
    firstName = firstName,
    lastName = lastName
)
