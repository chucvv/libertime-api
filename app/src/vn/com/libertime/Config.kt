package vn.com.libertime

import io.ktor.config.*
import io.ktor.util.*

/**
 * Class containing Configuration values or secret key which will be provided from
 * application.conf (from environment variables).
 */
@Suppress("PropertyName")
@KtorExperimentalAPI
class Config(environment: String, applicationConfig: ApplicationConfig) {
    val HASH_SECRET_KEY = applicationConfig.property("key.secret").getString()
    private val redisConfig = applicationConfig.config("redis.$environment")
    val CACHED_REDIS_HOST = redisConfig.property("host").getString()
    val CACHED_REDIS_PORT = redisConfig.property("port").getString().toInt()
    val REDIS_SECRET_KEY = redisConfig.property("secret").getString()
}