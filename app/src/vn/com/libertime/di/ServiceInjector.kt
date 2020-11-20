package vn.com.libertime.di

import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import vn.com.libertime.dao.DefaultUserDao
import vn.com.libertime.dao.UserDao
import vn.com.libertime.usermanagement.domain.service.UserService
import vn.com.libertime.usermanagement.service.DefaultUserService

@KoinApiExtension
object ServiceInjector {
    val userService = module {
        single { DefaultUserDao() as UserDao }
        single { DefaultUserService(get()) as UserService }
    }
}