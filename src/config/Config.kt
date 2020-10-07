package com.example.config

import io.ktor.config.*
import io.ktor.util.*

class Config(
    var host: String = defaultHost,
    var port: Int = defaultPort,
    var databaseHost: String = defaultDatabaseHost,
    var databasePort: Int = defaultDatabasePort
) {

    companion object {
        const val defaultHost = "localhost"
        const val defaultPort = 8080
        const val defaultDatabaseHost = "mongodb://127.0.0.1"
        const val defaultDatabasePort = 27017
    }

    @KtorExperimentalAPI
    infix fun of(applicationConfig: ApplicationConfig): Config {
        host = applicationConfig.property("host").getString()
        port = applicationConfig.property("port").getString().toInt()
        databaseHost = applicationConfig.property("databaseHost").getString()
        databasePort = applicationConfig.property("databasePort").getString().toInt()

        return this
    }
}