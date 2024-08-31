package ru.remsely.psihosom.security.service

import arrow.core.Either
import arrow.core.flatMap
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.remsely.psihosom.domain.error.DomainError
import ru.remsely.psihosom.domain.extentions.logger
import ru.remsely.psihosom.domain.user.User
import ru.remsely.psihosom.domain.user.dao.UserCreator
import ru.remsely.psihosom.security.jwt.JwtTokenGenerator
import ru.remsely.psihosom.usecase.auth.AuthService
import ru.remsely.psihosom.usecase.auth.UserLoginError
import ru.remsely.psihosom.usecase.auth.request.AuthRequest

@Service
open class AuthServiceImpl(
    private val userCreator: UserCreator,
    private val authManager: AuthenticationManager,
    private val tokenGenerator: JwtTokenGenerator,
    private val passwordEncoder: PasswordEncoder
) : AuthService {
    private val log = logger()

    @Transactional
    override fun registerUser(request: AuthRequest, role: User.Role): Either<DomainError, String> =
        userCreator.createUser(
            User(
                0L,
                request.username,
                passwordEncoder.encode(request.password),
                role
            )
        ).flatMap {
            loginUser(request)
        }.also {
            log.info("User with username ${request.username} successfully registered.")
        }

    override fun loginUser(request: AuthRequest): Either<DomainError, String> = Either.catch {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        ).let { auth ->
            tokenGenerator.generate(auth)
        }.also {
            log.info("User with username ${request.username} successfully logged in.")
        }
    }.mapLeft {
        UserLoginError.AuthenticationError(request.username)
    }
}
