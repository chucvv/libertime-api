package com.example

import com.example.controller.userRoutes
import com.example.di.liberModule
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.ktor.ext.Koin
import org.slf4j.event.Level

fun Application.setup() {
    install(Locations)
    install(DefaultHeaders) {
        header("X-Engine", "Ktor")
        header("X-Environment", "Dev")
    }
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
    install(Koin) {
        modules(liberModule)
    }
}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    setup()
    routing {
        userRoutes()
    }
}

