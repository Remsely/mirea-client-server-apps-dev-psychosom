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
import ru.remsely.psyhosom.db.repository.ProfileRepository
import ru.remsely.psyhosom.domain.account.dao.ProfileFinder
import ru.remsely.psyhosom.domain.account.dao.UserProfileFindingError
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.profile.Profile
import ru.remsely.psyhosom.domain.profile.dao.ProfileCreator
import ru.remsely.psyhosom.domain.profile.dao.ProfileUpdater
import ru.remsely.psyhosom.monitoring.log.logger

@Component
class ProfileDao(
    private val profileRepository: ProfileRepository
) : ProfileCreator, ProfileFinder, ProfileUpdater {
    private val log = logger()

    override fun createProfile(profile: Profile): Either<DomainError, Profile> =
        profileRepository.save(profile.toEntity())
            .toDomain()
            .right()
            .also {
                log.info("Profile for user with id ${profile.account.id} successfully created in DB.")
            }

    override fun findProfileByUserId(userId: Long): Either<DomainError, Profile> =
        profileRepository.findByAccountId(userId)
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
                !profileRepository.existsByTelegramEqualsIgnoreCaseOrPhoneEqualsIgnoreCase(
                    username,
                    username
                )
            ) {
                UserProfileFindingError.ProfileWithUsernameAlreadyExists
            }
        }

    override fun updateProfile(profile: Profile): Either<DomainError, Profile> =
        profileRepository.save(profile.toEntity())
            .toDomain()
            .right()
            .also {
                log.info("Profile for user with id ${profile.account.id} successfully updated in DB.")
            }
}
