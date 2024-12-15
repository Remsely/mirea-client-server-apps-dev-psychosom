package ru.remsely.psyhosom.telegram.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "telegram.bot")
class TelegramPropertiesBotCredentials {
    lateinit var token: String
    lateinit var username: String
}
