package vn.com.libertime.adapter.configuration

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import io.ktor.util.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import vn.com.libertime.adapter.client_side.um.kafka.consumeRegisterUser
import vn.com.libertime.common.log.Logger
import vn.com.libertime.database.Database
import vn.com.libertime.port.um.required.EnvironmentProvidable

@KtorExperimentalAPI
public class StorageConfiguration(
    private val environmentConfig: EnvironmentProvidable,
    private val logger: Logger
) : AppConfigurable {
    @ObsoleteCoroutinesApi
    override fun apply(application: Application) {
        environmentConfig.run {
            Database(HikariDataSource(HikariConfig(databaseConfig)))
        }
        environmentConfig.consumerKafkaConfig.run {
            GlobalScope.launch(newSingleThreadContext("consumerContext")) {
                consumeRegisterUser(bootstrapServers = bootstrapServers, schemaUrl = schemaUrl, log = logger)
            }
        }
    }
}