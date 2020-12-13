package vn.com.libertime.adapter.di

import io.ktor.util.*
import org.koin.core.module.Module
import org.koin.dsl.module
import vn.com.libertime.adapter.configuration.*
import vn.com.libertime.port.um.required.EnvironmentProvidable
import vn.com.libertime.serverconfig.EnvironmentProvider

@KtorExperimentalAPI
public object ConfigurationProvider {
    private val configurableModule = module {
        single { EnvironmentProvider() as EnvironmentProvidable }
        single { ServerSideConfiguration(get()) }
        single { JwtAppConfiguration(get(), get()) }
        single { StorageAppConfiguration(get(), get()) }
        single { BusinessAppConfiguration(get(), get(), get()) }
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