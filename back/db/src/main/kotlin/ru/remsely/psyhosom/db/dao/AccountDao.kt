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
import ru.remsely.psyhosom.domain.account.dao.AccountCreator
import ru.remsely.psyhosom.domain.account.dao.AccountFinder
import ru.remsely.psyhosom.domain.account.dao.UserCreationError
import ru.remsely.psyhosom.domain.account.dao.UserFindingError
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.monitoring.log.logger
import kotlin.jvm.optionals.getOrNull

@Component
open class AccountDao(
    private val accountRepository: AccountRepository
) : AccountCreator, AccountFinder {
    private val log = logger()

    @Transactional
    override fun createUser(account: Account): Either<DomainError, Account> = either {
        ensure(!accountRepository.existsByUsername(account.username)) {
            UserCreationError.AlreadyExists(account.username)
        }
        accountRepository.save(account.toEntity()).toDomain()
            .also {
                log.info("User with id ${it.id} successfully created in DB.")
            }
    }

    @Transactional(readOnly = true)
    override fun findUserByUsername(username: String): Either<DomainError, Account> = either {
        accountRepository.findByUsername(username)
            .let {
                ensureNotNull(it) { UserFindingError.NotFoundByUsername(username) }
                it.toDomain()
            }
            .also {
                log.info("User with id ${it.id} successfully found by username in DB.")
            }
    }

    override fun findUserById(id: Long): Either<DomainError, Account> =
        accountRepository.findById(id)
            .getOrNull()
            .toOption()
            .fold(
                { UserFindingError.NotFoundById(id).left() },
                {
                    it.toDomain().right().also {
                        log.info("User with id $id successfully found by id in DB.")
                    }
                }
            )
}
