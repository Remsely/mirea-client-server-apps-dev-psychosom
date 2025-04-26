package ru.remsely.psyhosom.telegram


import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import ru.remsely.psyhosom.domain.error.getOrThrowUnexpectedBehavior
import ru.remsely.psyhosom.domain.value_object.TelegramChatId
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.telegram.callback.Callback
import ru.remsely.psyhosom.telegram.callback.CancelConsultationCallback
import ru.remsely.psyhosom.telegram.properties.TelegramPropertiesBotCredentials

@Component
class PatientNotificationBot(
    private val cancelConsultationCallback: CancelConsultationCallback,
    commands: Set<BotCommand>,
    private val telegramPropertiesBotCredentials: TelegramPropertiesBotCredentials
) : TelegramLongPollingCommandBot(telegramPropertiesBotCredentials.token) {

    init {
        registerAll(*commands.toTypedArray())
    }

    private val log = logger()

    fun sendMessageWithKeyboard(chatId: TelegramChatId, text: String, keyboard: InlineKeyboardMarkup): Message =
        execute(
            SendMessage().apply {
                this.chatId = chatId.value.toString()
                this.text = text
                this.replyMarkup = keyboard
                this.parseMode = "Markdown"
            }
        ).also {
            log.info("Message with keyboard successfully sent to chat with id ${chatId.value}.")
        }

    override fun getBotUsername(): String = telegramPropertiesBotCredentials.username

    override fun processNonCommandUpdate(update: Update) {
        if (update.hasCallbackQuery()) {
            val callbackQuery = update.callbackQuery
            val data = callbackQuery.data
            val chatId = TelegramChatId(callbackQuery.message.chatId).getOrThrowUnexpectedBehavior()

            when {
                data.startsWith("/${Callback.CANCEL_CONSULTATION.value}") -> {
                    cancelConsultationCallback.execute(
                        absSender = this,
                        chatId = chatId,
                        args = data.split(" ").drop(1).toTypedArray()
                    )
                }
            }
        }
    }
}
