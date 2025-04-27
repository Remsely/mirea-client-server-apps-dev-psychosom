package ru.remsely.psyhosom.usecase.consultation

import arrow.core.Either
import arrow.core.raise.either
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.consultation.dao.ConsultationFinder
import ru.remsely.psyhosom.domain.consultation.dao.ConsultationUpdater
import ru.remsely.psyhosom.domain.consultation.extensions.finishConsultation
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.usecase.telegram.NotificationEvent

@Component
class InformFinishedConsultationsCommandImpl(
    private val consultationsFinder: ConsultationFinder,
    private val consultationsUpdater: ConsultationUpdater,
    private val eventPublisher: ApplicationEventPublisher
) : InformFinishedConsultationsCommand {

    override fun execute(): Either<DomainError, Unit> = either {
        consultationsFinder
            .findAllFinishedConsultationsToInform()
            .map {
                it.finishConsultation().bind()
            }
            .onEach { consultation ->
                eventPublisher.publishEvent(
                    NotificationEvent.ConsultationFinished(consultation)
                )
            }
            .let {
                consultationsUpdater.updateConsultations(it)
            }
    }
}
