package ru.remsely.psyhosom.usecase.user

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.remsely.psyhosom.domain.errors.DomainError
import ru.remsely.psyhosom.domain.extentions.logger
import ru.remsely.psyhosom.domain.user.User
import ru.remsely.psyhosom.domain.user.dao.ProfileFinder
import ru.remsely.psyhosom.domain.user.dao.ProfileUpdater
import ru.remsely.psyhosom.domain.user.dao.UserFinder
import ru.remsely.psyhosom.domain.user.event.UpdateUserProfileEvent
import ru.remsely.psyhosom.domain.value_object.PhoneNumber
import ru.remsely.psyhosom.domain.value_object.TelegramUsername

@Component
open class UserProfileManagerImpl(
    private val userFinder: UserFinder,
    private val profileFinder: ProfileFinder,
    private val profileUpdater: ProfileUpdater
) : UserProfileManager {
    private val log = logger()

    @Transactional
    override fun createOrUpdateProfile(event: UpdateUserProfileEvent): Either<DomainError, User.Profile> =
        userFinder.findUserById(event.userId)
            .flatMap {
                validatePossibleUsernameChange(event, it)
            }
            .flatMap { user ->
                profileFinder.findProfileByUserId(user.id!!)
                    .fold(
                        {
                            log.error("Profile with for user with id ${event.userId} not found.")
                            UserProfileManagingError.UserProfileNotFound(event.userId).left()
                        },
                        {
                            (user to it).right()
                        }
                    )

            }
            .flatMap { (user, profile) ->
                profileUpdater.updateProfile(
                    User.Profile(
                        id = profile.id,
                        user = user,
                        firstName = event.firstName ?: profile.firstName,
                        lastName = event.lastName ?: profile.lastName,
                        phone = if (event.phone?.value != null) event.phone else profile.phone,
                        telegram = if (event.telegram?.value != null) event.telegram else profile.telegram
                    )
                ).also {
                    log.info("Profile with id ${profile.id} successfully updated.")
                }
            }

    @Transactional(readOnly = true)
    override fun findProfileByUserId(userId: Long): Either<DomainError, User.Profile> =
        userFinder.findUserById(userId)
            .flatMap {
                profileFinder.findProfileByUserId(userId)
            }.also {
                log.info("Profile for user with id $userId successfully found.")
            }

    private fun validatePossibleUsernameChange(event: UpdateUserProfileEvent, user: User) = either {
        if (event.phone?.value != null && PhoneNumber(user.username).isRight()) {
            ensure(user.username == event.phone?.value) {
                UserProfileManagingError.ProfileUsernameMustBeInContacts
            }
        }
        if (event.telegram?.value != null && TelegramUsername(user.username).isRight()) {
            ensure(user.username == event.telegram?.value) {
                UserProfileManagingError.ProfileUsernameMustBeInContacts
            }
        }
        user
    }
}
