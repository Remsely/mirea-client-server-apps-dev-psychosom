package ru.remsely.psyhosom.usecase.consultation

import arrow.core.Either
import arrow.core.raise.either
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.consultation.dao.ConsultationFinder
import ru.remsely.psyhosom.domain.consultation.dao.ConsultationUpdater
import ru.remsely.psyhosom.domain.consultation.extensions.notifyConsultation
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.value_object.MeetingLink
import ru.remsely.psyhosom.usecase.telegram.NotificationEvent

@Component
class NotifyComingConsultationsCommandImpl(
    private val consultationsFinder: ConsultationFinder,
    private val consultationsUpdater: ConsultationUpdater,
    private val eventPublisher: ApplicationEventPublisher
) : NotifyComingConsultationsCommand {

    override fun execute(): Either<DomainError, Unit> =
        either {
            consultationsFinder
                .findAllConfirmedConsultationsToNotify()
                .map {
                    it.notifyConsultation(MeetingLink.generate()).bind()
                }
                .onEach { consultation ->
                    eventPublisher.publishEvent(
                        NotificationEvent.ConsultationNotified(consultation)
                    )
                }
                .let {
                    consultationsUpdater.updateConsultations(it)
                }
        }
}
