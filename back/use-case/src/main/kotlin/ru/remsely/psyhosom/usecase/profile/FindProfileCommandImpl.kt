package ru.remsely.psyhosom.usecase.profile

import arrow.core.Either
import arrow.core.flatMap
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.account.dao.AccountFinder
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.profile.Profile
import ru.remsely.psyhosom.domain.profile.dao.ProfileFinder
import ru.remsely.psyhosom.monitoring.log.logger

@Component
class FindProfileCommandImpl(
    private val accountFinder: AccountFinder,
    private val profileFinder: ProfileFinder
) : FindProfileCommand {
    private val log = logger()

    override fun execute(userId: Long): Either<DomainError, Profile> =
        accountFinder.findAccountById(userId)
            .flatMap {
                profileFinder.findProfileByUserId(userId)
            }.also {
                log.info("Profile for user with id $userId successfully found.")
            }
}
