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
import vn.com.libertime.shared.functions.library.initDB
import vn.com.libertime.shared.functions.library.initRedis
import vn.com.libertime.um.domain.entity.UserCredentialsEntity

fun main(args: Array<String>): Unit = EngineMain.main(args)

@KoinApiExtension
@KtorExperimentalAPI
fun Application.module() {
    val environment = System.getenv()["ENVIRONMENT"] ?: defaultEnvironment
    val applicationConfig = HoconApplicationConfig(ConfigFactory.load())
    with(Config(applicationConfig)) {
        JwtConfig.initialize(SECRET_KEY)
    }

    module {
        install(Koin) {
            modules(injectorSet)
        }
    }
    initDB(environment)
    initRedis(environment)
    setupCommonModules(environment)
    setupBusinessModules()
}

val ApplicationCall.user get() = authentication.principal<UserCredentialsEntity>()
