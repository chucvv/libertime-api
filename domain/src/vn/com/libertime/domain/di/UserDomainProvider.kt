package vn.com.libertime.domain.di

import org.koin.core.component.KoinApiExtension
import org.koin.core.module.Module
import org.koin.dsl.module
import vn.com.libertime.domain.um.*
import vn.com.libertime.port.um.provided.AuthService
import vn.com.libertime.port.um.provided.UserService

@KoinApiExtension
public object UserDomainProvider {
    private val usecases = module {
        factory { LoginUseCase(get(), get(), get()) }
        factory { RegisterUseCase(get(), get(), get(), get()) }
        factory { GetUserByIdUseCase(get()) }
        factory { UpdateUserInfoUseCase(get()) }
    }

    private val services = module {
        factory { DefaultAuthService(get(), get()) as AuthService }
        factory { DefaultUserService(get(), get()) as UserService }
    }

    public val dependencies: List<Module> = listOf(usecases, services)
}