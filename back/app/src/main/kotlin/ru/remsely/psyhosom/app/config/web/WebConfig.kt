package ru.remsely.psyhosom.app.config.web

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import ru.remsely.psyhosom.api.utils.AuthUserIdResolver

@Configuration
class WebConfig(
    private val authUserIdResolver: AuthUserIdResolver
) : WebMvcConfigurer{
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(authUserIdResolver)
    }
}
