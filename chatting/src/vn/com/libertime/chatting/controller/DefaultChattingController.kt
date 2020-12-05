package vn.com.libertime.chatting.controller

import io.ktor.http.cio.websocket.*
import io.ktor.sessions.*
import io.ktor.websocket.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import vn.com.libertime.chatting.model.ChatRoom
import vn.com.libertime.chatting.model.ClientSession

public class DefaultChattingController : ChattingController {
    private val chatRoom = ChatRoom()

    @ExperimentalCoroutinesApi
    override suspend fun onChat(socketSession: DefaultWebSocketServerSession) {
        val session = socketSession.call.sessions.get<ClientSession>() ?: return
        val incoming = socketSession.incoming
        try {
            incoming.consumeEach { frame ->
                when (frame) {
                    is Frame.Text -> {
                        val text = frame.readText()
                        chatRoom.receiveMessage(text, session.id, socketSession)
                    }
                    is Frame.Close -> {
                        chatRoom.leave(session.id)
                    }
                    else -> {
                    }
                }
            }
        } finally {
            chatRoom.leave(session.id)
        }
    }
}