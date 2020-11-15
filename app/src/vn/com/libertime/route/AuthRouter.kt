package vn.com.libertime.route

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.core.component.KoinApiExtension
import vn.com.libertime.shared.functions.library.FailureMessages
import vn.com.libertime.shared.functions.library.extension.respond
import vn.com.libertime.um.presentation.controller.AuthController
import vn.com.libertime.um.presentation.model.request.LoginRequest
import vn.com.libertime.um.presentation.model.request.RegisterRequest

@KoinApiExtension
fun Route.auth(controller: AuthController) {

    post("user") {
        val request = runCatching { call.receive<RegisterRequest>() }.getOrElse {
            throw BadRequestException(FailureMessages.MESSAGE_MISSING_CREDENTIALS)
        }
        val authResponse = controller.register(request.username, request.password)
        respond(authResponse)
    }

    post("authenticate") {
        val request = runCatching { call.receive<LoginRequest>() }.getOrElse {
            throw BadRequestException(FailureMessages.MESSAGE_MISSING_CREDENTIALS)
        }
        val authResponse = controller.login(request.username, request.password)
        respond(authResponse)
    }
}