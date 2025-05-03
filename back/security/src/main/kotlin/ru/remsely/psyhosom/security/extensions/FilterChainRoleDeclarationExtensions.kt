package ru.remsely.psyhosom.security.extensions

import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import ru.remsely.psyhosom.domain.account.Account

@DslMarker
private annotation class RoleDslMarker

@RoleDslMarker
class PublicDsl {
    internal val mappings = mutableListOf<Pair<HttpMethod, String>>()

    fun get(path: String) = mappings.add(HttpMethod.GET to path)
    fun post(path: String) = mappings.add(HttpMethod.POST to path)
    fun put(path: String) = mappings.add(HttpMethod.PUT to path)
    fun patch(path: String) = mappings.add(HttpMethod.PATCH to path)
    fun delete(path: String) = mappings.add(HttpMethod.DELETE to path)

    fun any(path: String) {
        HttpMethod.values().forEach { mappings += it to path }
    }
}

@RoleDslMarker
class RoleDsl {
    internal val mappings = mutableListOf<Pair<HttpMethod, String>>()

    fun get(path: String) = mappings.add(HttpMethod.GET to path)
    fun post(path: String) = mappings.add(HttpMethod.POST to path)
    fun put(path: String) = mappings.add(HttpMethod.PUT to path)
    fun patch(path: String) = mappings.add(HttpMethod.PATCH to path)
    fun delete(path: String) = mappings.add(HttpMethod.DELETE to path)
}

fun AuthorizeHttpRequestsConfigurer<HttpSecurity>
.AuthorizationManagerRequestMatcherRegistry.public(
    block: PublicDsl.() -> Unit
): AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry {
    val dsl = PublicDsl().apply(block)
    dsl.mappings.forEach { (method, path) ->
        this.requestMatchers(method, path).permitAll()
    }
    return this
}

fun AuthorizeHttpRequestsConfigurer<HttpSecurity>
.AuthorizationManagerRequestMatcherRegistry.withRole(
    role: Account.Role,
    block: RoleDsl.() -> Unit
): AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry {
    val dsl = RoleDsl().apply(block)
    dsl.mappings.forEach { (method, path) ->
        this.requestMatchers(method, path).hasRole(role.name)
    }
    return this
}
