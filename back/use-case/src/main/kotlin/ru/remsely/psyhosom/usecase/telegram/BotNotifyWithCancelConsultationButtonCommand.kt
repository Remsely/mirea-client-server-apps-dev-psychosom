package ru.remsely.psyhosom.usecase.telegram

import ru.remsely.psyhosom.domain.consultation.Consultation
import ru.remsely.psyhosom.domain.value_object.TelegramChatId

interface BotNotifyWithCancelConsultationButtonCommand {
    fun sendMessage(chatId: TelegramChatId, consultation: Consultation)
}
