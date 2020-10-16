package vn.com.libertime.statuspages

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*


data class MissingArgumentException(override val message: String = "Missing argument") : Exception()
data class StorageException(override val message: String = "Internal database error") : Exception()

fun StatusPages.Configuration.generalStatusPages() {
    exception<MissingArgumentException> { cause ->
        call.respond(HttpStatusCode.BadRequest, cause.message)
    }
    exception<StorageException> { cause ->
        call.respond(HttpStatusCode.InternalServerError, cause.message)
    }
    exception<UnknownError> {
        call.respond(HttpStatusCode.InternalServerError)
    }
    exception<IllegalArgumentException> {
        call.respond(HttpStatusCode.BadRequest)
    }
}