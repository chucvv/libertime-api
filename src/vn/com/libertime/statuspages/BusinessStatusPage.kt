package vn.com.libertime.statuspages

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import vn.com.libertime.shared.functions.library.FailureMessages

data class SystemException(override val message: String = FailureMessages.MESSAGE_FAILED) : Exception()
data class BusinessException(override val message: String = FailureMessages.MESSAGE_FAILED) : Exception()
data class AuthorizationException(override val message: String = "You are not authorised to use this service") :
    Exception()

fun StatusPages.Configuration.businessStatusPages() {
    exception<BusinessException> { cause ->
        call.respond(HttpStatusCode.BadRequest, cause.message)
    }
    exception<SystemException> { cause ->
        call.respond(HttpStatusCode.InternalServerError, cause.message)
    }
    exception<AuthorizationException> { cause ->
        call.respond(HttpStatusCode.Forbidden, cause.message)
    }
}