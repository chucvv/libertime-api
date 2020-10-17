package vn.com.libertime.application

import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import vn.com.libertime.config.Config
import vn.com.libertime.di.AuthInjector
import vn.com.libertime.di.DaoInjector
import vn.com.libertime.di.UserBusinessInjector
import vn.com.libertime.um.domain.entity.UserCredentialsEntity

@KoinApiExtension
@KtorExperimentalAPI
fun main(args: Array<String>) {
    val environment = System.getenv()["ENVIRONMENT"] ?: defaultEnvironment
    val hoconApplicationConfig = HoconApplicationConfig(ConfigFactory.load())
    val config: Config = Config() of hoconApplicationConfig.config("ktor.deployment.$environment")
    embeddedServer(Netty, port = config.port) {
        println("Starting instance in ${config.host}:${config.port}")
        module {
            install(Koin) {
                modules(
                    DaoInjector.database,
                    DaoInjector.userDao,
                    AuthInjector.authInjector,
                    UserBusinessInjector.useCases
                )
            }
        }
        setupModules(environment)
    }.start(wait = true)
}

val ApplicationCall.user get() = authentication.principal<UserCredentialsEntity>()
