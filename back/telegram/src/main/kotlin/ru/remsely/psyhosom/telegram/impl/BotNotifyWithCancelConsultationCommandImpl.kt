package ru.remsely.psyhosom.telegram.impl

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.remsely.psyhosom.domain.consultation.Consultation
import ru.remsely.psyhosom.domain.value_object.TelegramChatId
import ru.remsely.psyhosom.telegram.PatientNotificationBot
import ru.remsely.psyhosom.telegram.command.Command
import ru.remsely.psyhosom.usecase.telegram.BotNotifyWithCancelConsultationButtonCommand

@Component
class BotNotifyWithCancelConsultationCommandImpl(
    private val patientNotificationBot: PatientNotificationBot
) : BotNotifyWithCancelConsultationButtonCommand {
    override fun sendMessage(chatId: TelegramChatId, consultation: Consultation) {
        val cancelCommand = "/${Command.CANCEL_CONSULTATION.value} ${consultation.id}"

        val keyboardMarkup = InlineKeyboardMarkup().apply {
            keyboard = listOf(
                listOf(
                    InlineKeyboardButton().apply {
                        text = "Отменить запись"
                        callbackData = cancelCommand
                    }
                )
            )
        }

        val message = """
            Запись на консультацию принята! В ближайшее время с вами свяжется специалист для согласования времени.
            
            Вы можете отменить запись, нажав на кнопку ниже.
        """.trimIndent()

        patientNotificationBot.sendMessageWithKeyboard(chatId, message, keyboardMarkup)
    }
}
