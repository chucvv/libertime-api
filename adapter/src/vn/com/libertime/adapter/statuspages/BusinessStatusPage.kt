package vn.com.libertime.adapter.statuspages

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

internal data class SystemException(override val message: String? = FailureMessages.MESSAGE_FAILED) : Exception()
internal data class BusinessException(override val message: String? = FailureMessages.MESSAGE_FAILED) : Exception()
internal data class AuthorizationException(override val message: String = FailureMessages.MESSAGE_ACCESS_DENIED) :
    Exception()

internal fun StatusPages.Configuration.businessStatusPages() {
    exception<BusinessException> { cause ->
        call.respond(HttpStatusCode.BadRequest, cause.message ?: "")
    }
    exception<SystemException> { cause ->
        call.respond(HttpStatusCode.InternalServerError, cause.message ?: "")
    }
    exception<AuthorizationException> { cause ->
        call.respond(HttpStatusCode.Forbidden, cause.message)
    }
}