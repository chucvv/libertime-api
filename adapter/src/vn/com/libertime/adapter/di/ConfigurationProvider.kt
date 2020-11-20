package vn.com.libertime.adapter.di

import org.koin.dsl.module
import vn.com.libertime.adapter.configuration.BusinessAppConfiguration
import vn.com.libertime.adapter.configuration.CommonAppConfiguration
import vn.com.libertime.adapter.configuration.JwtAppConfiguration
import vn.com.libertime.adapter.server_side.defaultEnvironment

object ConfigurationProvider {
    val configuration = module {
        single { System.getenv()["ENVIRONMENT"] ?: defaultEnvironment }
        single { JwtAppConfiguration(get(), get()) }
        single { CommonAppConfiguration(get()) }
        single { BusinessAppConfiguration(get(), get()) }
    }

    val dependencies = listOf(configuration)
}