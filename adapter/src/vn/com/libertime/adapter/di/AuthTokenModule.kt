package vn.com.libertime.adapter.di

import io.ktor.util.*
import org.koin.core.module.Module
import org.koin.dsl.module
import vn.com.libertime.adapter.server_side.service.BCryptPassword
import vn.com.libertime.adapter.server_side.service.JwtConfigService
import vn.com.libertime.port.um.required.PasswordEncryptable
import vn.com.libertime.port.um.required.TokenProvidable

@KtorExperimentalAPI
public object AuthTokenModule {
    private val jwtModule = module {
        single<PasswordEncryptable> { BCryptPassword() }
        single<TokenProvidable> { JwtConfigService(get()) }
    }

    public val dependencies: List<Module> = listOf(jwtModule)
}