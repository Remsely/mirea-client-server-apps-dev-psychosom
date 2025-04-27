package ru.remsely.psyhosom.telegram

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import ru.remsely.psyhosom.domain.value_object.TelegramChatId

@Component
class BotMessageSender {
    fun sendMessage(chatId: TelegramChatId, text: String) =
        SendMessage(chatId.value.toString(), text)
            .apply {
                this.parseMode = "HTML"
                disableWebPagePreview()
            }

    fun sendMessageWithInlineButtons(chatId: TelegramChatId, text: String, keyboard: InlineKeyboardMarkup) =
        SendMessage()
            .apply {
                this.chatId = chatId.value.toString()
                this.text = text
                this.parseMode = "HTML"
                this.replyMarkup = keyboard
                disableWebPagePreview()
            }
}
