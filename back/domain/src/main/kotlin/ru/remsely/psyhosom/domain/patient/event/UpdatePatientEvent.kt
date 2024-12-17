package ru.remsely.psyhosom.domain.patient.event

import ru.remsely.psyhosom.domain.value_object.PhoneNumber
import ru.remsely.psyhosom.domain.value_object.TelegramUsername

data class UpdatePatientEvent(
    val accountId: Long,
    val firstName: String?,
    val lastName: String?,
    val phone: PhoneNumber?,
    val telegram: TelegramUsername?
)
