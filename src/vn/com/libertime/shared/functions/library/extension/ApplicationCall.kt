package vn.com.libertime.shared.functions.library.extension

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import vn.com.libertime.shared.functions.library.Response
import vn.com.libertime.shared.functions.library.generateHttpResponse

suspend fun PipelineContext<Unit, ApplicationCall>.respond(response: Response) {
    val httpResponse = generateHttpResponse(response)
    call.respond(httpResponse.code, httpResponse.body)
}