package vn.com.libertime.extension

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import vn.com.libertime.um.presentation.model.User

val ApplicationCall.user get() = authentication.principal<User>()

suspend fun PipelineContext<Unit, ApplicationCall>.sendOk() {
    call.respond(HttpStatusCode.OK)
}