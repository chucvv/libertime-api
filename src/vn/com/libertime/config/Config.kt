package vn.com.libertime.config

import io.ktor.config.*
import io.ktor.util.*

class Config(
    var host: String = defaultHost,
    var port: Int = defaultPort,
) {

    companion object {
        const val defaultHost = "localhost"
        const val defaultPort = 8080
    }

    @KtorExperimentalAPI
    infix fun of(applicationConfig: ApplicationConfig): Config {
        host = applicationConfig.property("host").getString()
        port = applicationConfig.property("port").getString().toInt()

        return this
    }
}