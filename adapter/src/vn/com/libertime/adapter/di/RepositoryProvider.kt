package vn.com.libertime.adapter.di

import org.koin.core.component.KoinApiExtension
import org.koin.core.module.Module
import org.koin.dsl.module
import vn.com.libertime.adapter.server_side.service.DefaultUserRepository
import vn.com.libertime.port.um.required.UserRepository

@KoinApiExtension
public object RepositoryProvider {
    private val userRepositoryModule = module {
        single { DefaultUserRepository(get()) as UserRepository }
    }

    public val dependencies: List<Module> = listOf(userRepositoryModule)
}