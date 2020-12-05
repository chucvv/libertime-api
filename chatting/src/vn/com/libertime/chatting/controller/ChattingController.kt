package vn.com.libertime.chatting.controller

import io.ktor.websocket.*

public interface ChattingController {
    public suspend fun onChat(socketSession: DefaultWebSocketServerSession)
}

public class ChattingControllerComposite : ChattingController {
    private val controllers: MutableList<ChattingController> = ArrayList()

    public fun register(chattingController: ChattingController): ChattingControllerComposite = apply {
        controllers.add(chattingController)
    }

    override suspend fun onChat(socketSession: DefaultWebSocketServerSession): Unit =
        controllers.forEach { it.onChat(socketSession) }
}