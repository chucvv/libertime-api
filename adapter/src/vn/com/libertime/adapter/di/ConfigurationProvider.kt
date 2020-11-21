package vn.com.libertime.adapter.di

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import io.ktor.config.*
import io.ktor.util.*
import org.koin.core.module.Module
import org.koin.dsl.module
import vn.com.libertime.adapter.configuration.*
import vn.com.libertime.adapter.server_side.defaultEnvironment

@KtorExperimentalAPI
public object ConfigurationProvider {

    private val env = System.getenv()["ENVIRONMENT"] ?: defaultEnvironment

    private val hikariConfig = HikariConfig("/${env}_hikari.properties").apply {
        maximumPoolSize = 3
        connectionTimeout = 30000
        leakDetectionThreshold = 2000
        validate()
    }

    private val configurableModule = module {
        single { env }
        single { hikariConfig }
        single { Config(get(), HoconApplicationConfig(ConfigFactory.load())) }
        single { ServerSideConfiguration(get()) }
        single { JwtAppConfiguration(get(), get()) }
        single { StorageAppConfiguration(get(), get()) }
        single { BusinessAppConfiguration(get(), get()) }
    }

    private val centerConfigModule = module {
        single {
            val serverSideConfiguration by inject<ServerSideConfiguration>()
            val storageAppConfiguration by inject<StorageAppConfiguration>()
            val jwtAppConfiguration by inject<JwtAppConfiguration>()
            val businessAppConfiguration by inject<BusinessAppConfiguration>()

            ConfigCenter()
                .register(serverSideConfiguration)
                .register(storageAppConfiguration)
                .register(jwtAppConfiguration)
                .register(businessAppConfiguration)
        }
    }

    public val dependencies: List<Module> = listOf(configurableModule, centerConfigModule)
}