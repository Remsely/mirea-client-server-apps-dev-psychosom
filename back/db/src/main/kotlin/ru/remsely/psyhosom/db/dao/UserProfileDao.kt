package ru.remsely.psyhosom.db.dao

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import arrow.core.singleOrNone
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.db.extensions.toDomain
import ru.remsely.psyhosom.db.extensions.toEntity
import ru.remsely.psyhosom.db.repository.UserProfileRepository
import ru.remsely.psyhosom.domain.errors.DomainError
import ru.remsely.psyhosom.domain.extentions.logger
import ru.remsely.psyhosom.domain.user.User
import ru.remsely.psyhosom.domain.user.dao.ProfileCreator
import ru.remsely.psyhosom.domain.user.dao.ProfileFinder
import ru.remsely.psyhosom.domain.user.dao.ProfileUpdater
import ru.remsely.psyhosom.domain.user.dao.UserProfileFindingError

@Component
class UserProfileDao(
    private val userProfileRepository: UserProfileRepository
) : ProfileCreator, ProfileFinder, ProfileUpdater {
    private val log = logger()

    override fun createProfile(profile: User.Profile): Either<DomainError, User.Profile> =
        userProfileRepository.save(profile.toEntity())
            .toDomain()
            .right()
            .also {
                log.info("Profile for user with id ${profile.user.id} successfully created in DB.")
            }

    override fun findProfileByUserId(userId: Long): Either<DomainError, User.Profile> =
        userProfileRepository.findByUser_Id(userId)
            .singleOrNone()
            .fold(
                { UserProfileFindingError.NotFoundByUserId(userId).left() },
                {
                    it.toDomain().right()
                        .also {
                            log.info("Profile for user with id $userId successfully found in DB.")
                        }
                }
            )

    override fun checkNotExistsWithUsernameInContacts(username: String): Either<DomainError, Unit> =
        either {
            ensure(
                !userProfileRepository.existsByTelegramEqualsIgnoreCaseOrPhoneEqualsIgnoreCase(
                    username,
                    username
                )
            ) {
                UserProfileFindingError.ProfileWithUsernameAlreadyExists
            }
        }

    override fun updateProfile(profile: User.Profile): Either<DomainError, User.Profile> =
        userProfileRepository.save(profile.toEntity())
            .toDomain()
            .right()
            .also {
                log.info("Profile for user with id ${profile.user.id} successfully updated in DB.")
            }
}
