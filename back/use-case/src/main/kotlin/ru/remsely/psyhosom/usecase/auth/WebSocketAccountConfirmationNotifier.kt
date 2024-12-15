package ru.remsely.psyhosom.usecase.auth

import ru.remsely.psyhosom.domain.value_object.TelegramBotToken

interface WebSocketAccountConfirmationNotifier {
    fun sendNotification(token: TelegramBotToken, status: Status)

    enum class Status {
        CONFIRMED,
        OUTDATED
    }
}
