package vn.com.libertime.chatting.model

import com.google.gson.Gson
import java.time.LocalDateTime

internal sealed class Broadcast(
    val type: Type,
    val timestamp: String = LocalDateTime.now().toString()
) {
    enum class Type {
        CHAT_MESSAGE,
        USER_JOINED,
        USER_LEFT
    }

    override fun toString(): String {
        return Gson().toJson(this)
    }
}

internal class ChatMessageBroadcast(
    val userId: String,
    val userName: String,
    val message: String
) : Broadcast(Type.CHAT_MESSAGE)

internal class UserJoinedBroadcast(
    val userId: String,
    val userName: String
) : Broadcast(Type.USER_JOINED)

internal class UserLeftBroadcast(
    val userId: String,
    val userName: String
) : Broadcast(Type.USER_LEFT)
