package ru.remsely.psyhosom.domain.user.event

import ru.remsely.psyhosom.domain.value_object.PhoneNumber
import ru.remsely.psyhosom.domain.value_object.TelegramUsername

data class UpdateUserProfileEvent(
    val userId: Long,
    val firstName: String?,
    val lastName: String?,
    val phone: PhoneNumber?,
    val telegram: TelegramUsername?
)
