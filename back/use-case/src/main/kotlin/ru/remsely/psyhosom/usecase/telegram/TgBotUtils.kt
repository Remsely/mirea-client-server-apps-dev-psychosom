package ru.remsely.psyhosom.usecase.telegram

import ru.remsely.psyhosom.domain.value_object.TelegramBotToken

interface TgBotUtils {
    fun getConfirmationUrl(token: TelegramBotToken): String
}
