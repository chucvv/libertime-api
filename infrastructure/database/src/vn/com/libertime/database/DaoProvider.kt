package vn.com.libertime.database

import org.koin.core.component.KoinApiExtension
import org.koin.core.module.Module
import org.koin.dsl.module
import vn.com.libertime.database.dao.DefaultUserDao
import vn.com.libertime.database.dao.UserDao

@KoinApiExtension
public object DaoProvider {
    private val userDao = module {
        single { DefaultUserDao() as UserDao }
    }

    public val dependencies: List<Module> = listOf(userDao)
}