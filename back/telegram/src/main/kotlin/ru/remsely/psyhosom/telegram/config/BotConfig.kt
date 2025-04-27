package ru.remsely.psyhosom.telegram.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import ru.remsely.psyhosom.telegram.PsychosomBot
import ru.remsely.psyhosom.telegram.properties.TelegramPropertiesBotCredentials
import ru.remsely.psyhosom.telegram.properties.TelegramPropertiesBotEndpoints

@Configuration
@EnableConfigurationProperties(TelegramPropertiesBotEndpoints::class, TelegramPropertiesBotCredentials::class)
open class BotConfig {
    @Bean
    open fun telegramBotsApi(bot: PsychosomBot): TelegramBotsApi =
        TelegramBotsApi(DefaultBotSession::class.java)
            .apply {
                registerBot(bot)
            }
}
