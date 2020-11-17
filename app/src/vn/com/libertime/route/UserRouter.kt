package vn.com.libertime.route

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.routing.*
import vn.com.libertime.auth.user
import vn.com.libertime.shared.functions.library.FailureMessages
import vn.com.libertime.shared.functions.library.extension.respond
import vn.com.libertime.statuspages.AuthorizationException
import vn.com.libertime.um.domain.entity.Credential
import vn.com.libertime.um.presentation.controller.UserController
import vn.com.libertime.um.presentation.model.request.UpdateProfileRequest

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