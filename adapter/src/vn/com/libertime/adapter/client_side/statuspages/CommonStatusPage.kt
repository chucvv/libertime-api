package vn.com.libertime.adapter.client_side.statuspages

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import vn.com.libertime.adapter.client_side.FailureMessages
import vn.com.libertime.common.State
import vn.com.libertime.common.generateResponse

fun StatusPages.Configuration.commonStatusPages() {
    status(HttpStatusCode.InternalServerError) {
        call.respond(it, generateResponse(State.FAILED, FailureMessages.MESSAGE_FAILED))
    }
    status(HttpStatusCode.Unauthorized) {
        call.respond(it, generateResponse(State.UNAUTHORIZED, FailureMessages.MESSAGE_ACCESS_DENIED))
    }
    exception<BadRequestException> {
        call.respond(
            HttpStatusCode.BadRequest,
            generateResponse(State.FAILED, it.message ?: FailureMessages.MESSAGE_FAILED)
        )
    }
    exception<UnknownError> {
        call.respond(HttpStatusCode.InternalServerError, generateResponse(State.FAILED, FailureMessages.MESSAGE_FAILED))
    }
}