package vn.com.libertime.adapter.di

import io.ktor.util.*
import org.koin.core.module.Module
import org.koin.dsl.module
import vn.com.libertime.adapter.configuration.BusinessAppConfiguration
import vn.com.libertime.adapter.configuration.CommonAppConfiguration
import vn.com.libertime.adapter.configuration.JwtAppConfiguration
import vn.com.libertime.adapter.configuration.ServerSideConfiguration
import vn.com.libertime.adapter.server_side.defaultEnvironment

@KtorExperimentalAPI
public object ConfigurationProvider {
    private val configuration = module {
        single { System.getenv()["ENVIRONMENT"] ?: defaultEnvironment }
        single { ServerSideConfiguration(get()) }
        single { JwtAppConfiguration(get(), get()) }
        single { CommonAppConfiguration(get()) }
        single { BusinessAppConfiguration(get(), get()) }
    }

    public val dependencies: List<Module> = listOf(configuration)
}