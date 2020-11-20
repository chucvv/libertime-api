package vn.com.libertime.adapter.di

import com.auth0.jwt.interfaces.JWTVerifier
import org.koin.dsl.module
import vn.com.libertime.adapter.server_side.service.BCryptPassword
import vn.com.libertime.adapter.server_side.service.JwtConfigService
import vn.com.libertime.port.um.required.PasswordEncryptable
import vn.com.libertime.port.um.required.TokenProvidable

object AuthTokenProvider {
    private val jwtModule = module {
        single<JWTVerifier> { JwtConfigService.instance.verifier }
        single<PasswordEncryptable> { BCryptPassword() }
        single<TokenProvidable> { JwtConfigService.instance }
    }

    val dependencies = listOf(jwtModule)
}