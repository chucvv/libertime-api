package vn.com.libertime

import io.ktor.application.*
import io.ktor.server.netty.*
import io.ktor.util.*
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import vn.com.libertime.adapter.configuration.ConfigCenter

internal fun main(args: Array<String>): Unit = EngineMain.main(args)

@KtorExperimentalAPI
internal fun Application.module() {
    module {
        install(Koin) {
            modules(injectedModules)
        }
    }

    val configCenter by inject<ConfigCenter>()
    configCenter.apply(this)
}