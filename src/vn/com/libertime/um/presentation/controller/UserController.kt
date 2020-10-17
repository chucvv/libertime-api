package vn.com.libertime.um.presentation.controller

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import vn.com.libertime.application.user
import vn.com.libertime.statuspages.AuthorizationException
import vn.com.libertime.um.domain.entity.UserCredentialsEntity
import vn.com.libertime.um.presentation.model.UserResponse

fun Route.userModule() {
    get("me") {
        val user: UserCredentialsEntity = call.user ?: throw AuthorizationException()
        call.respond(UserResponse(userId = user.userId, userName = user.userName)) // avoid sending
    }
}