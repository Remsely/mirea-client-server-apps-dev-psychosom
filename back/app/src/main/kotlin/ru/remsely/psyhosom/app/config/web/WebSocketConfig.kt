package ru.remsely.psyhosom.app.config.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import ru.remsely.psyhosom.api.web_socket.WebSocketAccountConfirmationHandler
import ru.remsely.psyhosom.api.web_socket.WebSocketSessionManager

@Configuration
@EnableWebSocket
class WebSocketConfig : WebSocketConfigurer {
    @Autowired
    private lateinit var webSocketSessionManager: WebSocketSessionManager

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(webSockerHandler(), "ws/auth/confirmation")
            .setAllowedOrigins("*")
    }

    @Bean
    fun webSockerHandler(): WebSocketHandler =
        WebSocketAccountConfirmationHandler(webSocketSessionManager)
}
