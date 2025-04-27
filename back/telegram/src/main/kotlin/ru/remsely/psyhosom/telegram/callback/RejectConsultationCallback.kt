package ru.remsely.psyhosom.telegram.callback

import org.springframework.stereotype.Component
import ru.remsely.psyhosom.usecase.consultation.RejectConsultationCommand

@Component
class RejectConsultationCallback(
    private val rejectConsultationCommand: RejectConsultationCommand,
) : BaseCallback(rejectConsultationCommand::execute)
