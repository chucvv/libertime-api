package vn.com.libertime.adapter.di

import com.auth0.jwt.interfaces.JWTVerifier
import org.koin.dsl.module
import vn.com.libertime.adapter.configuration.AppConfigurable
import vn.com.libertime.adapter.configuration.JwtAppConfiguration
import vn.com.libertime.adapter.user_management.service.BCryptPassword
import vn.com.libertime.adapter.user_management.service.JwtConfigService
import vn.com.libertime.port.required.EncryptedPassword
import vn.com.libertime.port.required.TokenProvidable

object AuthTokenProvider {
    private val jwtModule = module {
        single<JWTVerifier> { JwtConfigService.instance.verifier }
        single<EncryptedPassword> { BCryptPassword() }
        single<TokenProvidable> { JwtConfigService.instance }
        single { JwtAppConfiguration(get(), get()) as AppConfigurable }
    }

    val dependencies = listOf(jwtModule)
}