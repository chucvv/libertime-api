package vn.com.libertime.adapter.extension

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import vn.com.libertime.adapter.client_side.generateHttpResponse
import vn.com.libertime.adapter.client_side.um.model.Credential
import vn.com.libertime.common.Response

internal suspend fun PipelineContext<Unit, ApplicationCall>.respond(response: Response) {
    val httpResponse = generateHttpResponse(response)
    call.respond(httpResponse.code, httpResponse.body)
}

internal val ApplicationCall.user get() = authentication.principal<Credential>()