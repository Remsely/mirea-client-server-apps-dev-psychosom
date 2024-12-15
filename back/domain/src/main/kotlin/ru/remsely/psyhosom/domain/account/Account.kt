package ru.remsely.psyhosom.domain.account

import ru.remsely.psyhosom.domain.value_object.TelegramBotToken
import ru.remsely.psyhosom.domain.value_object.TelegramChatId
import java.time.LocalDateTime

data class Account(
    val id: Long,
    val username: String,
    val password: String,
    val role: Role,
    val isConfirmed: Boolean,
    val tgBotToken: TelegramBotToken,
    val tgChatId: TelegramChatId,
    val registrationDate: LocalDateTime
) {
    enum class Role {
        ADMIN, PATIENT, PSYCHOLOGIST
    }
}
