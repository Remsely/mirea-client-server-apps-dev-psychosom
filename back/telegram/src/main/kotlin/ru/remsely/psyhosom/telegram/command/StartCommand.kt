package ru.remsely.psyhosom.telegram.command

import arrow.core.raise.either
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.remsely.psyhosom.domain.error.getOrThrowUnexpectedBehavior
import ru.remsely.psyhosom.domain.value_object.TelegramBotToken
import ru.remsely.psyhosom.domain.value_object.TelegramChatId
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.telegram.BotMessageSender
import ru.remsely.psyhosom.usecase.auth.ConfirmAccountCommand
import ru.remsely.psyhosom.usecase.auth.WebSocketAccountConfirmationNotifier

@Component
open class StartCommand(
    private val botMessageSender: BotMessageSender,
    private val confirmAccountCommand: ConfirmAccountCommand,
    private val accountConfirmationNotifier: WebSocketAccountConfirmationNotifier
) : BotCommand(
    Command.START.value, "Начать"
) {
    private val log = logger()

    override fun execute(absSender: AbsSender, user: User, chat: Chat, args: Array<out String>?) {
        log.info("Command ${Command.START.value} was executed in chat ${chat.id} with args: $args")

        if (args.isNullOrEmpty() || args.size != 1) {
            val errorText = """
                ⚠️ <b>Не удалось выполнить команду /start</b>
                Убедитесь, что вы переходили по ссылке, указанной на сайте.
            """.trimIndent()

            absSender.execute(
                botMessageSender.sendMessage(
                    chatId = TelegramChatId(chat.id).getOrThrowUnexpectedBehavior(),
                    text = errorText
                )
            )
            return
        }

        val token = args[0]

        either {
            confirmAccountCommand.execute(
                token = TelegramBotToken(token).bind(),
                chatId = TelegramChatId(chat.id.toString()).bind()
            ).bind()
        }.fold(
            {
                val errorText = """
                    ⏰ <b>Время подтверждения истекло</b>
                    Попробуйте зарегистрироваться заново.
                """.trimIndent()

                absSender.execute(
                    botMessageSender.sendMessage(
                        chatId = TelegramChatId(chat.id).getOrThrowUnexpectedBehavior(),
                        text = errorText
                    )
                )
                log.warn("Error while executing command ${Command.START.value} in chat ${chat.id} with args: $args.")
            },
            {
                log.info("Command ${Command.START.value} successfully executed in chat ${chat.id} with args: $args.")

                accountConfirmationNotifier.sendNotification(
                    token = TelegramBotToken(token).getOrThrowUnexpectedBehavior(),
                    status = WebSocketAccountConfirmationNotifier.Status.CONFIRMED
                )

                val successText = """
                    🎉 <b>Поздравляем!</b>
                    Ваш аккаунт успешно подтверждён.
                """.trimIndent()

                absSender.execute(
                    botMessageSender.sendMessage(
                        chatId = TelegramChatId(chat.id).getOrThrowUnexpectedBehavior(),
                        text = successText
                    )
                )
            }
        )
    }
}
