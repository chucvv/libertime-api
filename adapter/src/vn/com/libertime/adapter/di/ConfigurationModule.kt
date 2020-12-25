package vn.com.libertime.adapter.di

import io.ktor.util.*
import mu.KotlinLogging
import org.koin.core.module.Module
import org.koin.dsl.module
import vn.com.libertime.adapter.configuration.*
import vn.com.libertime.adapter.server_side.log.DefaultLogger
import vn.com.libertime.port.um.required.Logger
import vn.com.libertime.port.um.required.EnvironmentProvidable
import vn.com.libertime.serverconfig.EnvironmentProvider

@KtorExperimentalAPI
public object ConfigurationModule {
    private val configurableModule = module {
        single { DefaultLogger(KotlinLogging) as Logger }
        single { EnvironmentProvider() as EnvironmentProvidable }
        single { GlobalConfiguration(get()) }
        single { JwtAppConfiguration(get(), get()) }
        single { StorageConfiguration(get(), get()) }
        single { BusinessAppConfiguration(get(), get(), get(), get()) }
    }

    private val centerConfigModule = module {
        single {
            val serverSideConfiguration by inject<GlobalConfiguration>()
            val storageAppConfiguration by inject<StorageConfiguration>()
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