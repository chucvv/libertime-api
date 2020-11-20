package vn.com.libertime.adapter.di

import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import vn.com.libertime.adapter.um.service.DefaultUserRepository
import vn.com.libertime.dao.DefaultUserDao
import vn.com.libertime.dao.UserDao
import vn.com.libertime.port.um.required.UserRepository

@KoinApiExtension
object RepositoryProvider {
    private val userRepository = module {
        single { DefaultUserDao() as UserDao }
        single { DefaultUserRepository(get()) as UserRepository }
    }

    val dependencies = listOf(userRepository)
}