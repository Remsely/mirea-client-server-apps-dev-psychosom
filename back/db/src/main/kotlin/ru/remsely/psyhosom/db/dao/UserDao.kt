package ru.remsely.psyhosom.db.dao

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.ensureNotNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.remsely.psyhosom.db.extensions.toDomain
import ru.remsely.psyhosom.db.extensions.toEntity
import ru.remsely.psyhosom.db.repository.UserRepository
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.user.User
import ru.remsely.psyhosom.domain.user.dao.UserCreationError
import ru.remsely.psyhosom.domain.user.dao.UserCreator
import ru.remsely.psyhosom.domain.user.dao.UserFinder
import ru.remsely.psyhosom.domain.user.dao.UserFindingError

@Component
open class UserDao(
    private val userRepository: UserRepository
) : UserCreator, UserFinder {
    @Transactional
    override fun createUser(user: User): Either<DomainError, User> = either {
        ensure(!userRepository.existsByUsername(user.username)) {
            UserCreationError.AlreadyExists(user.username)
        }
        userRepository.save(user.toEntity()).toDomain()
    }

    @Transactional(readOnly = true)
    override fun findUserByUsername(username: String): Either<DomainError, User> = either {
        userRepository.findByUsername(username)
            .let {
                ensureNotNull(it) { UserFindingError.NotFound(username) }
                it.toDomain()
            }
    }
}
