package vn.com.libertime

import io.ktor.application.*
import io.ktor.server.netty.*
import io.ktor.util.*
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import vn.com.libertime.adapter.configuration.BusinessAppConfiguration
import vn.com.libertime.adapter.configuration.CommonAppConfiguration
import vn.com.libertime.adapter.configuration.JwtAppConfiguration
import vn.com.libertime.adapter.configuration.ServerSideConfiguration

internal fun main(args: Array<String>): Unit = EngineMain.main(args)

@KtorExperimentalAPI
internal fun Application.module() {
    module {
        install(Koin) {
            modules(injectedModules)
        }
    }
    val serverSideConfiguration by inject<ServerSideConfiguration>()
    serverSideConfiguration.apply(this)

    val commonAppConfiguration by inject<CommonAppConfiguration>()
    commonAppConfiguration.apply(this)

    val jwtVerifier by inject<JwtAppConfiguration>()
    jwtVerifier.apply(this)

    val businessAppConfiguration by inject<BusinessAppConfiguration>()
    businessAppConfiguration.apply(this)
}