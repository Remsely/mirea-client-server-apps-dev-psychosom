package ru.remsely.psyhosom.domain.profile.event

import ru.remsely.psyhosom.domain.value_object.PhoneNumber
import ru.remsely.psyhosom.domain.value_object.TelegramUsername

data class UpdateProfileEvent(
    val userId: Long,
    val firstName: String?,
    val lastName: String?,
    val phone: PhoneNumber?,
    val telegram: TelegramUsername?
)
