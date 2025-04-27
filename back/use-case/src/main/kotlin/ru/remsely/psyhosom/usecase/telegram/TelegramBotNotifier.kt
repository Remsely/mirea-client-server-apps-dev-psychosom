package ru.remsely.psyhosom.usecase.telegram

import ru.remsely.psyhosom.domain.consultation.Consultation

interface TelegramBotNotifier {
    fun notify(event: NotificationEvent)
}

sealed class NotificationEvent {
    data class ConsultationCreated(val consultation: Consultation) : NotificationEvent()
    data class ConsultationCancelled(val consultation: Consultation) : NotificationEvent()
    data class ConsultationRejected(val consultation: Consultation) : NotificationEvent()
    data class ConsultationConfirmed(val consultation: Consultation) : NotificationEvent()
    data class ConsultationNotified(val consultation: Consultation) : NotificationEvent()
    data class ConsultationFinished(val consultation: Consultation) : NotificationEvent()
}
