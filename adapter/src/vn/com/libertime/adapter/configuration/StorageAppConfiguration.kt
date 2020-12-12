package vn.com.libertime.adapter.configuration

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import io.ktor.util.*
import vn.com.libertime.adapter.server_side.cache.Redis
import vn.com.libertime.database.Database
import vn.com.libertime.port.um.required.EnvironmentProvidable

@KtorExperimentalAPI
public class StorageAppConfiguration(
    private val environmentConfig: EnvironmentProvidable
) : AppConfigurable {

    override fun apply(application: Application) {
        environmentConfig.cachingClusterConfig.run {
            Redis(host, port, secretKey)
        }

        Database(HikariDataSource(HikariConfig(environmentConfig.databaseConfig)))
    }
}