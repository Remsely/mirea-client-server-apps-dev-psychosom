package ru.remsely.psyhosom.usecase.telegram

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class NotificationEventListener(
    private val tgBotNotifier: TelegramBotNotifier,
) {
    @EventListener
    fun onConsultationEvent(event: NotificationEvent) {
        tgBotNotifier.notify(event)
    }
}
