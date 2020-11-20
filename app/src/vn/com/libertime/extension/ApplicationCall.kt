package vn.com.libertime.extension

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import vn.com.libertime.common.Response
import vn.com.libertime.shared.functions.library.generateHttpResponse

suspend fun PipelineContext<Unit, ApplicationCall>.respond(response: Response) {
    val httpResponse = generateHttpResponse(response)
    call.respond(httpResponse.code, httpResponse.body)
}