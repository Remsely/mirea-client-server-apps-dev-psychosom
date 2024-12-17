package ru.remsely.psyhosom.telegram.callback

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.remsely.psyhosom.domain.value_object.TelegramChatId
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.telegram.BotMessageSender
import ru.remsely.psyhosom.telegram.command.Command
import ru.remsely.psyhosom.usecase.consultation.CancelConsultationCommand

@Component
class CancelConsultationCallback(
    private val botMessageSender: BotMessageSender,
    private val cancelConsultationCommand: CancelConsultationCommand,
) {
    private val log = logger()

    fun execute(absSender: AbsSender, chatId: TelegramChatId, args: Array<out String>?) {
        log.info("Command ${Command.CANCEL_CONSULTATION.value} was executed in chat ${chatId.value} with args: $args")

        if (args.isNullOrEmpty() || args.size != 1) {
            sendErrorMessage(absSender, chatId)
            return
        }

        val consultationId = runCatching {
            args[0].toLong()
        }.getOrElse {
            sendErrorMessage(absSender, chatId)
            return
        }

        cancelConsultationCommand.execute(
            consultationId = consultationId,
            chatId = chatId
        )
            .fold(
                { sendErrorMessage(absSender, chatId) },
                {
                    absSender.execute(
                        botMessageSender.sendMessage(
                            chatId.value.toString(),
                            "Запись отменена."
                        )
                    )
                }
            )
    }

    private fun sendErrorMessage(absSender: AbsSender, chatId: TelegramChatId) {
        absSender.execute(
            botMessageSender.sendMessage(
                chatId.value.toString(),
                "При выполнении команды ${Command.START.value} произошла ошибка."
            )
        )
    }
}
