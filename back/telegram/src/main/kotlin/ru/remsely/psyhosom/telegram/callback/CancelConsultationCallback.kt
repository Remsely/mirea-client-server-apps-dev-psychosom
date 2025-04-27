package ru.remsely.psyhosom.telegram.callback

import org.springframework.stereotype.Component
import ru.remsely.psyhosom.usecase.consultation.CancelConsultationCommand

@Component
class CancelConsultationCallback(
    private val cancelConsultationCommand: CancelConsultationCommand,
) : BaseCallback(cancelConsultationCommand::execute)
