package ru.remsely.psyhosom.security.service

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.account.Account
import ru.remsely.psyhosom.domain.account.dao.AccountCreator
import ru.remsely.psyhosom.domain.account.event.RegisterPsychologistEvent
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.psychologist.Article
import ru.remsely.psyhosom.domain.psychologist.Psychologist
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistCreator
import ru.remsely.psyhosom.domain.schedule.Schedule
import ru.remsely.psyhosom.domain.value_object.PhoneNumber
import ru.remsely.psyhosom.domain.value_object.TelegramBotToken
import ru.remsely.psyhosom.domain.value_object.TelegramChatId
import ru.remsely.psyhosom.domain.value_object.TelegramUsername
import ru.remsely.psyhosom.usecase.auth.AccountCreatedEvent
import ru.remsely.psyhosom.usecase.auth.PsychologistRegisterValidationError
import ru.remsely.psyhosom.usecase.auth.RegisterPsychologistCommand
import ru.remsely.psyhosom.usecase.s3.S3FileCreator
import ru.remsely.psyhosom.usecase.telegram.TgBotUtils
import java.time.LocalDateTime

@Component
class RegisterPsychologistCommandImpl(
    private val accountCreator: AccountCreator,
    private val passwordEncoder: PasswordEncoder,
    private val psychologistCreator: PsychologistCreator,
    private val tgBotUtils: TgBotUtils,
    private val s3FileCreator: S3FileCreator
) : RegisterPsychologistCommand {
    override fun execute(event: RegisterPsychologistEvent): Either<DomainError, AccountCreatedEvent> = either {
        ensure(
            !(PhoneNumber(event.username).getOrNone().isNone() &&
                    TelegramUsername(event.username).getOrNone().isNone())
        ) {
            PsychologistRegisterValidationError.InvalidUsername
        }

        val s3File = s3FileCreator.createFile(event.profileImage).bind()

        val account = accountCreator.createAccount(
            Account(
                id = 0L,
                username = event.username,
                password = passwordEncoder.encode(event.password),
                role = event.role,
                isConfirmed = false,
                tgBotToken = TelegramBotToken.generate(),
                tgChatId = TelegramChatId(null).bind(),
                registrationDate = LocalDateTime.now()
            )
        ).bind()

        psychologistCreator.createPsychologist(
            Psychologist(
                id = 0L,
                account = account,
                firstName = event.firstName,
                lastName = event.lastName,
                profileImage = s3File.url,
                article = Article.empty(),
                educations = emptyList(),
                schedule = Schedule.empty()
            )
        ).bind()

        AccountCreatedEvent(
            confirmationUrl = tgBotUtils.getConfirmationUrl(account.tgBotToken),
            webSocketToken = account.tgBotToken
        )
    }
}
