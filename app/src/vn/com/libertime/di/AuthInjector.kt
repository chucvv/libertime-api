package vn.com.libertime.di

import com.auth0.jwt.interfaces.JWTVerifier
import org.koin.dsl.module
import vn.com.libertime.auth.JwtConfig
import vn.com.libertime.auth.TokenProvider
import vn.com.libertime.usermanagement.domain.service.PasswordManager
import vn.com.libertime.usermanagement.domain.service.PasswordManagerContract

object AuthInjector {
    val authInjector = module {
        single<JWTVerifier> { JwtConfig.instance.verifier }
        single<PasswordManagerContract> { PasswordManager() }
        single<TokenProvider> { JwtConfig.instance }
    }
}