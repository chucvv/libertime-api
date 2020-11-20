package vn.com.libertime.route

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.routing.*
import vn.com.libertime.adapter.client_side.um.controller.UserController
import vn.com.libertime.adapter.client_side.um.model.Credential
import vn.com.libertime.adapter.client_side.um.request.UpdateProfileRequest
import vn.com.libertime.application.user
import vn.com.libertime.extension.respond
import vn.com.libertime.shared.functions.library.FailureMessages
import vn.com.libertime.statuspages.AuthorizationException

fun Route.user(controller: UserController) {
    route("me") {
        get {
            val user: Credential = call.user ?: throw AuthorizationException()
            val meResponse = controller.getProfile(user.userId)
            respond(meResponse)
        }

        put {
            val user: Credential = call.user ?: throw AuthorizationException()
            val request = runCatching { call.receive<UpdateProfileRequest>() }.getOrElse {
                throw BadRequestException(FailureMessages.MESSAGE_MISSING_ARGUMENTS)
            }
            val meResponse = controller.updateProfile(request.toUpdateUserParam(user.userId))
            respond(meResponse)
        }
    }
}