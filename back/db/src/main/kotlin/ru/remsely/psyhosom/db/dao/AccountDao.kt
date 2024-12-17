package ru.remsely.psyhosom.db.dao

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.ensureNotNull
import arrow.core.right
import arrow.core.toOption
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.remsely.psyhosom.db.extensions.toDomain
import ru.remsely.psyhosom.db.extensions.toEntity
import ru.remsely.psyhosom.db.repository.AccountRepository
import ru.remsely.psyhosom.domain.account.Account
import ru.remsely.psyhosom.domain.account.dao.*
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.dao.PatientEraser
import ru.remsely.psyhosom.domain.value_object.TelegramBotToken
import ru.remsely.psyhosom.domain.value_object.TelegramChatId
import ru.remsely.psyhosom.monitoring.log.logger
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

@Component
open class AccountDao(
    private val accountRepository: AccountRepository,
    private val patientEraser: PatientEraser
) : AccountCreator, AccountFinder, AccountUpdater, AccountEraser {
    private val log = logger()

    @Transactional
    override fun createAccount(account: Account): Either<DomainError, Account> = either {
        ensure(!accountRepository.existsByUsername(account.username)) {
            AccountCreationError.AlreadyExists(account.username)
        }
        accountRepository.save(account.toEntity()).toDomain()
            .also {
                log.info("Account with id ${it.id} successfully created in DB.")
            }
    }

    @Transactional(readOnly = true)
    override fun findAccountByUsername(username: String): Either<DomainError, Account> = either {
        accountRepository.findByUsername(username)
            .let {
                ensureNotNull(it) { AccountFindingError.NotFoundByUsername(username) }
                it.toDomain()
            }
            .also {
                log.info("Account with id ${it.id} successfully found by username in DB.")
            }
    }

    @Transactional(readOnly = true)
    override fun findAccountById(id: Long): Either<DomainError, Account> =
        accountRepository.findById(id)
            .getOrNull()
            .toOption()
            .fold(
                { AccountFindingError.NotFoundById(id).left() },
                {
                    it.toDomain().right().also {
                        log.info("Account with id $id successfully found by id in DB.")
                    }
                }
            )

    @Transactional(readOnly = true)
    override fun findAccountByTgBotToken(tgBotToken: TelegramBotToken): Either<DomainError, Account> =
        accountRepository.findByTgBotToken(tgBotToken.value)
            .toOption()
            .fold(
                { AccountFindingError.NotFoundByTgBotToken(tgBotToken).left() },
                {
                    it.toDomain().right().also {
                        log.info("Account with token ${tgBotToken.value} successfully found by in DB.")
                    }
                }
            )

    @Transactional(readOnly = true)
    override fun findOutdatedAccounts(): List<Account> =
        LocalDateTime.now().minusMinutes(5)
            .let { outdatedDate ->
                accountRepository.findOutdatedAccounts(outdatedDate)
                    .map { it.toDomain() }
            }.also {
                log.info("Found ${it.size} outdated accounts.")
            }

    @Transactional
    override fun confirmAccount(id: Long, tgChatId: TelegramChatId): Either<DomainError, Account> =
        findAccountById(id).fold(
            { AccountUpdatingError.NotFoundById(id).left() },
            {
                accountRepository.save(
                    it.toEntity().copy(
                        isConfirmed = true,
                        tgChatId = tgChatId.value
                    )
                ).toDomain().right().also {
                    log.info("Account with id $id successfully confirmed in DB.")
                }
            }
        )

    @Transactional
    override fun eraseAccountsByIds(ids: List<Long>): Either<DomainError, Unit> =
        patientEraser.erasePatientsByAccountIds(ids)
            .let {
                accountRepository.deleteAllById(ids).right()
            }
            .also {
                log.info("Accounts by id list with size ${ids.size} successfully deleted from DB.")
            }
}
