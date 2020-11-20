package vn.com.libertime.adapter.configuration

import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.util.*
import vn.com.libertime.adapter.server_side.cache.Redis
import vn.com.libertime.adapter.server_side.database.Database
import vn.com.libertime.adapter.server_side.service.JwtConfigService

@Suppress("PropertyName")
@KtorExperimentalAPI
class Config(environment: String, applicationConfig: ApplicationConfig) {
    val HASH_SECRET_KEY = applicationConfig.property("key.secret").getString()
    private val redisConfig = applicationConfig.config("redis.$environment")
    val CACHED_REDIS_HOST = redisConfig.property("host").getString()
    val CACHED_REDIS_PORT = redisConfig.property("port").getString().toInt()
    val REDIS_SECRET_KEY = redisConfig.property("secret").getString()
}

@KtorExperimentalAPI
class ServerSideConfiguration(
    private val environment: String
) : AppConfigurable {

    override fun apply(application: Application) {
        val applicationConfig = HoconApplicationConfig(ConfigFactory.load())
        with(Config(environment, applicationConfig)) {
            JwtConfigService.initialize(HASH_SECRET_KEY)
            Redis(CACHED_REDIS_HOST, CACHED_REDIS_PORT, REDIS_SECRET_KEY)
        }
        Database(environment)
    }
}