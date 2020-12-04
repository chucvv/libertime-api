package vn.com.libertime.chatting.route

import io.ktor.routing.*
import io.ktor.websocket.*
import vn.com.libertime.chatting.controller.ChattingController

public fun Route.chatting(controller: ChattingController) {
    webSocket("/communicate") {
        controller.onChat(this)
    }
}