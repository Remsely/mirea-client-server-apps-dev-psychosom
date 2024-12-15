package ru.remsely.psyhosom.api.web_socket

import arrow.core.getOrElse
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import ru.remsely.psyhosom.domain.value_object.TelegramBotToken
import ru.remsely.psyhosom.monitoring.log.logger

@Component
class WebSocketAccountConfirmationHandler(
    private val sessionManager: WebSocketSessionManager
) : TextWebSocketHandler() {
    private val log = logger()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val token = TelegramBotToken(
            session.uri!!.query!!.split("=")[1]
        ).getOrElse {
            throw RuntimeException("Invalid token.")
        }

        session.attributes["token"] = token.value
        sessionManager.addSession(
            token = token,
            session = session
        )
        log.info("WebSocket connection for account confirmation was established for user with token ${token.value}.")
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val token = session.attributes["token"] as String
        sessionManager.removeSession(
            token = TelegramBotToken(token).getOrElse {
                throw RuntimeException("Invalid token.")
            }
        )
        log.info("Web socket session with token $token was closed.")
    }
}
