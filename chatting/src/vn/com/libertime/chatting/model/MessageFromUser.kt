package vn.com.libertime.chatting.model

import com.google.gson.GsonBuilder
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory

internal sealed class MessageFromUser {
    enum class Type {
        CHAT_MESSAGE,
        JOIN_REQUEST,
        LEAVE_REQUEST
    }

    companion object {
        fun parseFromString(string: String): MessageFromUser {
            val runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(MessageFromUser::class.java, "type")
                .registerSubtype(JoinRequestFromUser::class.java, Type.JOIN_REQUEST.name)
                .registerSubtype(LeaveRequestFromUser::class.java, Type.LEAVE_REQUEST.name)
                .registerSubtype(ChatMessageFromUser::class.java, Type.CHAT_MESSAGE.name)

            return GsonBuilder()
                .registerTypeAdapterFactory(runtimeTypeAdapterFactory)
                .create()
                .fromJson(string, MessageFromUser::class.java)
        }
    }
}

internal class JoinRequestFromUser(
    val userName: String
) : MessageFromUser()

internal object LeaveRequestFromUser : MessageFromUser()

internal class ChatMessageFromUser(
    val message: String
) : MessageFromUser()