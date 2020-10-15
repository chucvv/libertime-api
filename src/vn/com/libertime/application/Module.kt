package vn.com.libertime.application

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.content.*
import io.ktor.jackson.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.core.component.KoinApiExtension
import org.slf4j.event.Level
import vn.com.libertime.application.statuspages.authStatusPages
import vn.com.libertime.application.statuspages.generalStatusPages
import vn.com.libertime.um.presentation.controller.userRoutes

fun isProduction(environment: String): Boolean = environment == productionEnvironment

@KoinApiExtension
fun Application.setupModules(environment: String) {
    install(Locations)
    install(DefaultHeaders) {
        header("X-Engine", "Ktor")
        header("X-Environment", environment)
    }
    install(CallLogging) {
        level = if (isProduction(environment)) {
            Level.INFO
        } else {
            Level.WARN
        }
        filter { call -> call.request.path().startsWith("/") }
    }
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
    install(StatusPages) {
        generalStatusPages()
        authStatusPages()
    }
    install(Routing) {
        static("/static") {
            resources("static")
        }
        userRoutes()
    }
}