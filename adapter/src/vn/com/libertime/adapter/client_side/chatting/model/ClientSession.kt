package vn.com.libertime.adapter.client_side.chatting.model

import io.ktor.websocket.*
import java.util.concurrent.atomic.AtomicInteger

public class ClientSession(public val session: DefaultWebSocketServerSession) {
    public companion object {
        public var lastId: AtomicInteger = AtomicInteger(0)
    }

    private val id: Int = lastId.getAndIncrement()
    public val name: String = "user$id"
}