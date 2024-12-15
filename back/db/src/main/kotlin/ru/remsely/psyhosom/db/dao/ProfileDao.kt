package ru.remsely.psyhosom.db.dao

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import arrow.core.singleOrNone
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.remsely.psyhosom.db.extensions.toDomain
import ru.remsely.psyhosom.db.extensions.toEntity
import ru.remsely.psyhosom.db.repository.ProfileRepository
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.profile.Profile
import ru.remsely.psyhosom.domain.profile.dao.*
import ru.remsely.psyhosom.monitoring.log.logger

@Component
open class ProfileDao(
    private val profileRepository: ProfileRepository
) : ProfileCreator, ProfileFinder, ProfileUpdater, ProfileEraser {
    private val log = logger()

    @Transactional
    override fun createProfile(profile: Profile): Either<DomainError, Profile> =
        profileRepository.save(profile.toEntity())
            .toDomain()
            .right()
            .also {
                log.info("Profile for user with id ${profile.account.id} successfully created in DB.")
            }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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

    @Transactional
    override fun updateProfile(profile: Profile): Either<DomainError, Profile> =
        profileRepository.save(profile.toEntity())
            .toDomain()
            .right()
            .also {
                log.info("Profile for user with id ${profile.account.id} successfully updated in DB.")
            }

    @Transactional
    override fun eraseProfilesByAccountIds(accountIds: List<Long>): Either<DomainError, Unit> =
        profileRepository.deleteByAccountIdIn(accountIds).right()
            .also {
                log.info("Profiles by id list wish size ${accountIds.size} successfully deleted from DB.")
            }
}
