package vn.com.libertime.adapter.client_side.chatting.route

import io.ktor.routing.*
import io.ktor.websocket.*
import vn.com.libertime.adapter.client_side.chatting.controller.ChattingController

internal fun Route.chatting(controller: ChattingController) {
    webSocket("/communicate") {
        controller.onChat(this)
    }
}