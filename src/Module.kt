package com.example

import com.example.controller.userRoutes
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.content.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.routing.*
import org.slf4j.event.Level

fun isProduction(environment: String): Boolean = environment == productionEnvironment

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
    install(ContentNegotiation) { gson { } }
    install(Routing) {
        static("/static") {
            resources("static")
        }
        userRoutes()
    }
}