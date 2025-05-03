package ru.remsely.psyhosom.api.utils

import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import ru.remsely.psyhosom.usecase.auth.AuthCredentialsAnnotationsProvider

@Component
class AuthUserIdResolver(
    private val authCredentialsAnnotationsProvider: AuthCredentialsAnnotationsProvider
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean =
        parameter.getParameterAnnotation(AuthUserId::class.java) != null &&
                parameter.parameterType == Long::class.javaPrimitiveType

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any = authCredentialsAnnotationsProvider.getAuthUserId()
}
