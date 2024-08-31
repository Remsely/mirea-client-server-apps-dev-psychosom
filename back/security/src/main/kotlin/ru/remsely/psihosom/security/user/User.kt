package ru.remsely.psihosom.security.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.remsely.psihosom.domain.user.User

data class User(
    val id: Long,
    private val username: String,
    private val password: String,
    val role: User.Role
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority(role.name))

    override fun getPassword(): String = password

    override fun getUsername(): String = username
}
