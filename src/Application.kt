package com.example

import com.example.config.Config
import com.example.di.liberModule
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*
import org.koin.dsl.module
import org.koin.ktor.ext.Koin

@KtorExperimentalAPI
fun main(args: Array<String>) {
    val environment = System.getenv()["ENVIRONMENT"] ?: defaultEnvironment
    val hoconApplicationConfig = HoconApplicationConfig(ConfigFactory.load())
    val config: Config = Config() of hoconApplicationConfig.config("ktor.deployment.$environment")
    embeddedServer(Netty, port = config.port) {
        println("Starting instance in ${config.host}:${config.port}")
        module {
            install(Koin) {
                modules(liberModule)
            }
        }
        setupModules(environment)
    }.start(wait = true)
}

