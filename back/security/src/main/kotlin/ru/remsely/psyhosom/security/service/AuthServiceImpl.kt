package ru.remsely.psyhosom.security.service

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.remsely.psyhosom.domain.account.Account
import ru.remsely.psyhosom.domain.account.dao.AccountCreator
import ru.remsely.psyhosom.domain.account.dao.ProfileFinder
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.profile.Profile
import ru.remsely.psyhosom.domain.profile.dao.ProfileCreator
import ru.remsely.psyhosom.domain.value_object.PhoneNumber
import ru.remsely.psyhosom.domain.value_object.TelegramUsername
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.security.jwt.JwtTokenGenerator
import ru.remsely.psyhosom.usecase.auth.AuthService
import ru.remsely.psyhosom.usecase.auth.UserLoginError
import ru.remsely.psyhosom.usecase.auth.UserRegisterValidationError

@Service
open class AuthServiceImpl(
    private val accountCreator: AccountCreator,
    private val authManager: AuthenticationManager,
    private val tokenGenerator: JwtTokenGenerator,
    private val passwordEncoder: PasswordEncoder,
    private val profileCreator: ProfileCreator,
    private val profileFinder: ProfileFinder
) : AuthService {
    private val log = logger()

    @Transactional
    override fun registerUser(account: Account): Either<DomainError, String> = either {
        ensure(
            !(PhoneNumber(account.username).getOrNone().isNone() &&
                    TelegramUsername(account.username).getOrNone().isNone())
        ) {
            UserRegisterValidationError.InvalidUsername
        }
        profileFinder.checkNotExistsWithUsernameInContacts(account.username).bind()
        accountCreator.createUser(
            Account(
                0L,
                account.username,
                passwordEncoder.encode(account.password),
                account.role
            )
        ).bind().let {
            profileCreator.createProfile(
                Profile(
                    id = null,
                    account = it,
                    firstName = null,
                    lastName = null,
                    phone = PhoneNumber(it.username).getOrNull(),
                    telegram = TelegramUsername(it.username).getOrNull()
                )
            ).bind()
            loginUser(account).bind()
                .also {
                    log.info("User with username ${account.username} successfully registered.")
                }
        }
    }

    override fun loginUser(account: Account): Either<DomainError, String> = Either.catch {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(account.username, account.password)
        ).let { auth ->
            tokenGenerator.generate(auth)
        }.also {
            log.info("User with username ${account.username} successfully logged in.")
        }
    }.mapLeft {
        UserLoginError.AuthenticationError(account.username)
    }
}
