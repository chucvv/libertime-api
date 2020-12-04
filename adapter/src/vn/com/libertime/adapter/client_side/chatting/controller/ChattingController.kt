package vn.com.libertime.adapter.client_side.chatting.controller

import io.ktor.websocket.*

public interface ChattingController {
    public suspend fun onChat(session: DefaultWebSocketServerSession)
}

public class ChattingControllerComposite : ChattingController {
    private val controllers: MutableList<ChattingController> = ArrayList()

    public fun register(chattingController: ChattingController): ChattingControllerComposite = apply {
        controllers.add(chattingController)
    }

    override suspend fun onChat(session: DefaultWebSocketServerSession): Unit =
        controllers.forEach { it.onChat(session) }
}