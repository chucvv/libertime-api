package com.example.di

import com.example.databaseHost
import com.example.databaseName
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val liberModule = module {
    single { KMongo.createClient(databaseHost).coroutine.getDatabase(databaseName) }
}
