package ru.remsely.psyhosom.security.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.user.dao.UserFinder
import ru.remsely.psyhosom.security.user.toDetails

@Component
class UserDetailsServiceImpl(
    private val userFinder: UserFinder
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails =
        username?.let {
            userFinder.findUserByUsername(it)
                .fold(
                    { throw UsernameNotFoundException("User $username not found.") },
                    { user -> user.toDetails() }
                )
        } ?: throw UsernameNotFoundException("Username not found.")
}
