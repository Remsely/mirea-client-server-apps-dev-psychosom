package ru.remsely.psyhosom.telegram.impl

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.value_object.TelegramBotToken
import ru.remsely.psyhosom.telegram.properties.TelegramPropertiesBotEndpoints
import ru.remsely.psyhosom.usecase.telegram.TelegramBotConfirmationUris

@Component
class TelegramBotConfirmationUrisImpl(
    @Qualifier("telegramPropertiesBotEndpoints")
    private val telegramBotEndpoints: TelegramPropertiesBotEndpoints
) : TelegramBotConfirmationUris {
    override fun getTelegramConfirmationUri(token: TelegramBotToken): String =
        "${telegramBotEndpoints.confirmation}${token.value}"
}
