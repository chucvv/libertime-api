package vn.com.libertime.adapter.configuration

import com.zaxxer.hikari.HikariConfig
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.util.*
import vn.com.libertime.adapter.server_side.cache.Redis
import vn.com.libertime.database.Database
import vn.com.libertime.port.um.required.EnvironmentProvidable

@Suppress("PropertyName")
@KtorExperimentalAPI
public class Config(applicationConfig: ApplicationConfig) {
    public val HASH_SECRET_KEY: String = applicationConfig.property("key.secret").getString()
}

@KtorExperimentalAPI
public class StorageAppConfiguration(
    private val environmentConfig: EnvironmentProvidable
) : AppConfigurable {

    override fun apply(application: Application) {
        environmentConfig.cachingClusterConfig.run {
            Redis(host, port, secretKey)
        }
        Database(HikariConfig("/${environmentConfig.deployEnvironment}_hikari.properties").apply {
            maximumPoolSize = 3
            connectionTimeout = 30000
            leakDetectionThreshold = 2000
            validate()
        })
    }
}