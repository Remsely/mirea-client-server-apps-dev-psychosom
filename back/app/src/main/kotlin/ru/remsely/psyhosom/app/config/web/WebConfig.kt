package ru.remsely.psyhosom.app.config.web

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import ru.remsely.psyhosom.api.utils.annotations.resolver.AuthAccountIdResolver
import ru.remsely.psyhosom.api.utils.annotations.resolver.AuthPatientIdResolver
import ru.remsely.psyhosom.api.utils.annotations.resolver.AuthPsychologistIdResolver

@Configuration
class WebConfig(
    private val authAccountIdResolver: AuthAccountIdResolver,
    private val authPatientIdResolver: AuthPatientIdResolver,
    private val authPsychologistIdResolver: AuthPsychologistIdResolver
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.apply {
            add(authAccountIdResolver)
            add(authPatientIdResolver)
            add(authPsychologistIdResolver)
        }
    }
}
