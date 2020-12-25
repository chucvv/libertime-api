package vn.com.libertime.adapter.client_side.um.route

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import vn.com.libertime.adapter.client_side.um.controller.UserLocationController
import vn.com.libertime.adapter.client_side.um.model.Credential
import vn.com.libertime.adapter.extension.respond
import vn.com.libertime.adapter.extension.user
import vn.com.libertime.adapter.statuspages.AuthorizationException
import vn.com.libertime.adapter.statuspages.FailureMessages

internal fun Route.search(controller: UserLocationController) {
    route("search") {
        get("radius") {
            val user: Credential = call.user ?: throw AuthorizationException()
            val radius = call.request.queryParameters["radius"]?.toDouble()
                ?: throw BadRequestException(FailureMessages.MESSAGE_MISSING_ARGUMENTS)
            val searchResult = controller.searchInRadius(userId = user.userId, radius = radius)
            respond(searchResult)
        }
    }
}