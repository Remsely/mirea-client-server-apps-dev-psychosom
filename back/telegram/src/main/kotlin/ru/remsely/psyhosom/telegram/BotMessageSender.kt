package ru.remsely.psyhosom.telegram

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Component
class BotMessageSender {
    fun sendMessage(chatId: String, text: String) =
        SendMessage(chatId, text)
            .apply {
                enableMarkdown(true)
                disableWebPagePreview()
            }
}
