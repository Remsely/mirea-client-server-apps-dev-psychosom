package ru.remsely.psyhosom.usecase.profile

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.account.Account
import ru.remsely.psyhosom.domain.account.dao.AccountFinder
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.profile.Profile
import ru.remsely.psyhosom.domain.profile.dao.ProfileFinder
import ru.remsely.psyhosom.domain.profile.dao.ProfileUpdater
import ru.remsely.psyhosom.domain.profile.event.UpdateProfileEvent
import ru.remsely.psyhosom.domain.value_object.PhoneNumber
import ru.remsely.psyhosom.domain.value_object.TelegramUsername
import ru.remsely.psyhosom.monitoring.log.logger

@Component
class UpdateProfileCommandImpl(
    private val accountFinder: AccountFinder,
    private val profileFinder: ProfileFinder,
    private val profileUpdater: ProfileUpdater,
) : UpdateProfileCommand {
    private val log = logger()

    override fun execute(event: UpdateProfileEvent): Either<DomainError, Profile> =
        accountFinder.findAccountById(event.accountId)
            .flatMap {
                validatePossibleUsernameChange(event, it)
            }
            .flatMap { user ->
                profileFinder.findProfileByUserId(user.id)
                    .fold(
                        {
                            log.error("Profile with for user with id ${event.accountId} not found.")
                            ProfileUpdateError.ProfileNotFound(event.accountId).left()
                        },
                        {
                            (user to it).right()
                        }
                    )
            }
            .flatMap { (user, profile) ->
                profileUpdater.updateProfile(
                    Profile(
                        id = profile.id,
                        account = user,
                        firstName = event.firstName ?: profile.firstName,
                        lastName = event.lastName ?: profile.lastName,
                        phone = if (event.phone?.value != null) event.phone else profile.phone,
                        telegram = if (event.telegram?.value != null) event.telegram else profile.telegram
                    )
                ).also {
                    log.info("Profile with id ${profile.id} successfully updated.")
                }
            }

    private fun validatePossibleUsernameChange(event: UpdateProfileEvent, account: Account) = either {
        if (event.phone?.value != null && PhoneNumber(account.username).isRight()) {
            ensure(account.username == event.phone?.value) {
                ProfileUpdateError.ProfileUsernameMustBeInContacts
            }
        }
        if (event.telegram?.value != null && TelegramUsername(account.username).isRight()) {
            ensure(account.username == event.telegram?.value) {
                ProfileUpdateError.ProfileUsernameMustBeInContacts
            }
        }
        account
    }
}
