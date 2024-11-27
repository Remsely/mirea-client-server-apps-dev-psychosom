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
import ru.remsely.psyhosom.db.repository.UserRepository
import ru.remsely.psyhosom.domain.errors.DomainError
import ru.remsely.psyhosom.domain.extentions.logger
import ru.remsely.psyhosom.domain.user.User
import ru.remsely.psyhosom.domain.user.dao.UserCreationError
import ru.remsely.psyhosom.domain.user.dao.UserCreator
import ru.remsely.psyhosom.domain.user.dao.UserFinder
import ru.remsely.psyhosom.domain.user.dao.UserFindingError
import kotlin.jvm.optionals.getOrNull

@Component
open class UserDao(
    private val userRepository: UserRepository
) : UserCreator, UserFinder {
    private val log = logger()

    @Transactional
    override fun createUser(user: User): Either<DomainError, User> = either {
        ensure(!userRepository.existsByUsername(user.username)) {
            UserCreationError.AlreadyExists(user.username)
        }
        userRepository.save(user.toEntity()).toDomain()
            .also {
                log.info("User with id ${it.id} successfully created in DB.")
            }
    }

    @Transactional(readOnly = true)
    override fun findUserByUsername(username: String): Either<DomainError, User> = either {
        userRepository.findByUsername(username)
            .let {
                ensureNotNull(it) { UserFindingError.NotFoundByUsername(username) }
                it.toDomain()
            }
            .also {
                log.info("User with id ${it.id} successfully found by username in DB.")
            }
    }

    override fun findUserById(id: Long): Either<DomainError, User> =
        userRepository.findById(id)
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
