package vn.com.libertime.adapter.configuration

import com.zaxxer.hikari.HikariConfig
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.util.*
import vn.com.libertime.adapter.server_side.cache.Redis
import vn.com.libertime.database.Database

@Suppress("PropertyName")
@KtorExperimentalAPI
public class Config(environment: String, applicationConfig: ApplicationConfig) {
    public val HASH_SECRET_KEY: String = applicationConfig.property("key.secret").getString()
    private val redisConfig = applicationConfig.config("redis.$environment")
    public val CACHED_REDIS_HOST: String = redisConfig.property("host").getString()
    public val CACHED_REDIS_PORT: Int = redisConfig.property("port").getString().toInt()
    public val REDIS_SECRET_KEY: String = redisConfig.property("secret").getString()
}

@KtorExperimentalAPI
public class StorageAppConfiguration(
    private val hikariConfig: HikariConfig,
    private val config: Config
) : AppConfigurable {

    override fun apply(application: Application) {
        config.run {
            Redis(CACHED_REDIS_HOST, CACHED_REDIS_PORT, REDIS_SECRET_KEY)
        }
        Database(hikariConfig)
    }
}