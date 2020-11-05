package vn.com.libertime.statuspages

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import vn.com.libertime.shared.functions.library.FailureMessages.MESSAGE_ACCESS_DENIED
import vn.com.libertime.shared.functions.library.FailureMessages.MESSAGE_FAILED
import vn.com.libertime.shared.functions.library.State
import vn.com.libertime.shared.functions.library.generateResponse

fun StatusPages.Configuration.commonStatusPages() {
    status(HttpStatusCode.InternalServerError) {
        call.respond(it, generateResponse(State.FAILED, MESSAGE_FAILED))
    }
    status(HttpStatusCode.Unauthorized) {
        call.respond(it, generateResponse(State.UNAUTHORIZED, MESSAGE_ACCESS_DENIED))
    }
    exception<BadRequestException> {
        call.respond(HttpStatusCode.BadRequest, generateResponse(State.FAILED, it.message ?: MESSAGE_FAILED))
    }
    exception<UnknownError> {
        call.respond(HttpStatusCode.InternalServerError, generateResponse(State.FAILED, MESSAGE_FAILED))
    }
}