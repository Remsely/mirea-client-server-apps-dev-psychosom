package ru.remsely.psyhosom.telegram.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "telegram.bot.endpoints")
class TelegramPropertiesBotEndpoints {
    lateinit var confirmation: String
}
