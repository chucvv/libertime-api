package vn.com.libertime.um.presentation.controller

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import vn.com.libertime.application.user
import vn.com.libertime.statuspages.AuthorizationException
import vn.com.libertime.um.domain.entity.UserEntity
import vn.com.libertime.um.presentation.model.UserResponse

fun Route.userModule() {
    get("me") {
        val user: UserEntity = call.user ?: throw AuthorizationException()
        call.respond(UserResponse(userName = user.userName, email = user.email)) // avoid sending
    }
}