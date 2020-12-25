package vn.com.libertime.adapter.di

import org.koin.core.module.Module
import org.koin.dsl.module
import vn.com.libertime.cache.CacheRedis
import vn.com.libertime.database.repository.DefaultUserLocationRepository
import vn.com.libertime.database.repository.DefaultUserRepository
import vn.com.libertime.port.um.required.Cacheable
import vn.com.libertime.port.um.required.EnvironmentProvidable
import vn.com.libertime.port.um.required.UserLocationRepository
import vn.com.libertime.port.um.required.UserRepository

public object RepositoryModule {
    private val cacheStorage = module {
        single {
            val environmentConfig by inject<EnvironmentProvidable>()
            environmentConfig.cachingClusterConfig.run {
                return@single CacheRedis(host, port, secretKey) as Cacheable
            }
        }
    }
    private val userRepository = module {
        single { DefaultUserRepository() as UserRepository }
        single { DefaultUserLocationRepository() as UserLocationRepository }
    }
    public val dependencies: List<Module> = listOf(cacheStorage, userRepository)
}