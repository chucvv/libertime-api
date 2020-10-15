package vn.com.libertime.application.statuspages

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*


data class MissingArgumentException(override val message: String = "Missing argument") : Exception()

fun StatusPages.Configuration.generalStatusPages() {
    exception<MissingArgumentException> { cause ->
        call.respond(HttpStatusCode.BadRequest, cause.message)
    }
    exception<UnknownError> {
        call.respond(HttpStatusCode.InternalServerError)
    }
    exception<IllegalArgumentException> {
        call.respond(HttpStatusCode.BadRequest)
    }
}