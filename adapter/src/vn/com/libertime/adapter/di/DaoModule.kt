package vn.com.libertime.adapter.di

import org.koin.core.module.Module
import org.koin.dsl.module
import vn.com.libertime.database.dao.DefaultUserDao
import vn.com.libertime.port.um.required.UserDao

public object DaoModule {
    private val userDao = module {
        single { DefaultUserDao() as UserDao }
    }
    public val dependencies: List<Module> = listOf(userDao)
}