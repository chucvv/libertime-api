package vn.com.libertime.application

import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.config.*
import io.ktor.server.netty.*
import io.ktor.util.*
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import vn.com.libertime.Database
import vn.com.libertime.Redis
import vn.com.libertime.adapter.user_management.model.Credential
import vn.com.libertime.adapter.user_management.service.JwtConfigService
import vn.com.libertime.di.injectedModules

fun main(args: Array<String>): Unit = EngineMain.main(args)

@KoinApiExtension
@KtorExperimentalAPI
fun Application.module() {
    val environment = System.getenv()["ENVIRONMENT"] ?: defaultEnvironment
    val applicationConfig = HoconApplicationConfig(ConfigFactory.load())
    with(Config(environment, applicationConfig)) {
        JwtConfigService.initialize(HASH_SECRET_KEY)
        Redis(CACHED_REDIS_HOST, CACHED_REDIS_PORT, REDIS_SECRET_KEY)
    }
    Database(environment)
    module {
        install(Koin) {
            modules(injectedModules)
        }
    }
    setupCommonModules(environment)
    setupBusinessModules()
}

val ApplicationCall.user get() = authentication.principal<Credential>()