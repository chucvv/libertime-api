package vn.com.libertime.di

import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import vn.com.libertime.um.data.dao.DefaultUserDao
import vn.com.libertime.um.domain.repository.UserDao

@KoinApiExtension
object DaoInjector {
    val userDao = module {
        single { DefaultUserDao() as UserDao }
    }
}