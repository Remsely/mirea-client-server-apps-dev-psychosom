package ru.remsely.psyhosom.telegram


import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Update
import ru.remsely.psyhosom.telegram.callback.*
import ru.remsely.psyhosom.telegram.properties.TelegramPropertiesBotCredentials

@Component
class PsychosomBot(
    commands: MutableSet<out BotCommand>,
    private val cancelConsultationCallback: CancelConsultationCallback,
    private val telegramPropertiesBotCredentials: TelegramPropertiesBotCredentials,
    private val rejectConsultationCallback: RejectConsultationCallback,
    private val confirmConsultationCallback: ConfirmConsultationCallback
) : TelegramLongPollingCommandBot(telegramPropertiesBotCredentials.token) {

    init {
        registerAll(*commands.toTypedArray())
    }

    override fun getBotUsername(): String = telegramPropertiesBotCredentials.username

    override fun processNonCommandUpdate(update: Update) =
        CallbackData(this, update)
            .fold(
                {
                    // TODO: придумать какую-нибудь обработку
                },
                { callbackData ->
                    when {
                        callbackData.rawData.startsWith("/${Callback.CANCEL_CONSULTATION.value}") ->
                            cancelConsultationCallback.execute(callbackData)

                        callbackData.rawData.startsWith("/${Callback.REJECT_CONSULTATION.value}") ->
                            rejectConsultationCallback.execute(callbackData)

                        callbackData.rawData.startsWith("/${Callback.CONFIRM_CONSULTATION.value}") ->
                            confirmConsultationCallback.execute(callbackData)
                    }
                }
            )
}
