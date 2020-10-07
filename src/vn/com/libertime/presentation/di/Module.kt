package vn.com.libertime.presentation.di

import vn.com.libertime.presentation.databaseHost
import vn.com.libertime.presentation.databaseName
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val liberModule = module {
    single { KMongo.createClient(databaseHost).coroutine.getDatabase(databaseName) }
}
