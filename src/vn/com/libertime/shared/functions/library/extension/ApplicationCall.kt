package vn.com.libertime.shared.functions.library.extension

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*

suspend fun PipelineContext<Unit, ApplicationCall>.sendOk(data: Any? = null) {
    call.respond(HttpStatusCode.OK, data ?: "")
}