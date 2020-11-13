package vn.com.libertime.di

import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import vn.com.libertime.um.data.service.DefaultUserService
import vn.com.libertime.um.data.service.dao.DefaultUserDao
import vn.com.libertime.um.data.service.dao.UserDao
import vn.com.libertime.um.domain.service.UserService

@KoinApiExtension
object ServiceInjector {
    val userService = module {
        single { DefaultUserDao() as UserDao }
        single { DefaultUserService(get()) as UserService }
    }
}