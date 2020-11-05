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
import vn.com.libertime.auth.Config
import vn.com.libertime.auth.JwtConfig
import vn.com.libertime.defaultEnvironment
import vn.com.libertime.di.injectorSet
import vn.com.libertime.shared.functions.library.Database
import vn.com.libertime.shared.functions.library.Redis
import vn.com.libertime.um.domain.entity.UserCredentialsEntity

fun main(args: Array<String>): Unit = EngineMain.main(args)

@KoinApiExtension
@KtorExperimentalAPI
fun Application.module() {
    val environment = System.getenv()["ENVIRONMENT"] ?: defaultEnvironment
    val applicationConfig = HoconApplicationConfig(ConfigFactory.load())
    with(Config(environment, applicationConfig)) {
        JwtConfig.initialize(SECRET_KEY)
        Redis(CACHED_REDIS_HOST, CACHED_REDIS_PORT)
    }
    Database(environment)
    module {
        install(Koin) {
            modules(injectorSet)
        }
    }
    setupCommonModules(environment)
    setupBusinessModules()
}

val ApplicationCall.user get() = authentication.principal<UserCredentialsEntity>()
