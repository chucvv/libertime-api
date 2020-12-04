package vn.com.libertime.adapter.client_side.chatting.controller

import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import vn.com.libertime.adapter.client_side.chatting.model.ClientSession
import java.util.*
import kotlin.collections.LinkedHashSet

public class DefaultChattingController : ChattingController {
    private val clients = Collections.synchronizedSet(LinkedHashSet<ClientSession>())
    override suspend fun onChat(session: DefaultWebSocketServerSession) {
        val clientSession = ClientSession(session)
        clients += clientSession
        val incoming = session.incoming
        try {
            while (true) {
                when (val frame = incoming.receive()) {
                    is Frame.Text -> {
                        val text = frame.readText()
                        // Iterate over all the connections
                        val textToSend = "${clientSession.name} said: $text"
                        for (other in clients.toList()) {
                            other.session.outgoing.send(Frame.Text(textToSend))
                        }
                    }
                }
            }
        } finally {
            clients -= clientSession
        }
    }
}