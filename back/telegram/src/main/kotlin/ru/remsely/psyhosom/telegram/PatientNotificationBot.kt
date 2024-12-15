package ru.remsely.psyhosom.telegram


import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Update
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.telegram.properties.TelegramPropertiesBotCredentials

@Component
class PatientNotificationBot(
    private val botMessageSender: BotMessageSender,
    commands: Set<BotCommand>,
    private val telegramPropertiesBotCredentials: TelegramPropertiesBotCredentials
) : TelegramLongPollingCommandBot(telegramPropertiesBotCredentials.token) {

    init {
        registerAll(*commands.toTypedArray())
    }

    private val log = logger()

    override fun getBotUsername(): String = telegramPropertiesBotCredentials.username

    override fun processNonCommandUpdate(update: Update) {
        if (update.hasMessage()) {
            val chatId = update.message.chatId.toString()
            if (update.message.hasText()) {
                execute(
                    botMessageSender.sendMessage(chatId, "Вы написали: *${update.message.text}*")
                )
            } else {
                execute(
                    botMessageSender.sendMessage(chatId, "Я понимаю только текст!")
                )
            }
        }
    }
}
