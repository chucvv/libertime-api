package vn.com.libertime.adapter.di

import org.koin.core.module.Module
import org.koin.dsl.module
import vn.com.libertime.database.DefaultUserRepository
import vn.com.libertime.port.um.required.UserRepository

public object DaoModule {
    private val userDao = module {
        single { DefaultUserRepository() as UserRepository }
    }
    public val dependencies: List<Module> = listOf(userDao)
}