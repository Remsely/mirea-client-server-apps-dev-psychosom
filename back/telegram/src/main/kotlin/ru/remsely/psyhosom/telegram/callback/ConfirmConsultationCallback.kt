package ru.remsely.psyhosom.telegram.callback

import org.springframework.stereotype.Component
import ru.remsely.psyhosom.usecase.consultation.ConfirmConsultationCommand

@Component
class ConfirmConsultationCallback(
    private val confirmConsultationCommand: ConfirmConsultationCommand
) : BaseCallback(confirmConsultationCommand::execute)
