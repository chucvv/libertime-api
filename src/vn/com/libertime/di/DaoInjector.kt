package vn.com.libertime.di

import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import org.litote.kmongo.KMongo
import vn.com.libertime.application.databaseHost
import vn.com.libertime.application.databaseName
import vn.com.libertime.um.data.dao.DefaultUserDao
import vn.com.libertime.um.domain.repository.UserDao

@KoinApiExtension
object DaoInjector {
    val database = module { single { KMongo.createClient(databaseHost).getDatabase(databaseName) } }
    val userDao = module {
        single { DefaultUserDao(get()) as UserDao }
    }
}