package ru.remsely.psyhosom.usecase.telegram

import ru.remsely.psyhosom.domain.value_object.TelegramBotToken

interface TelegramBotConfirmationUris {
    fun getTelegramConfirmationUri(token: TelegramBotToken): String
}
