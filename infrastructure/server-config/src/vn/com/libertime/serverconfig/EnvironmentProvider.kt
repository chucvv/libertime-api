package vn.com.libertime.serverconfig

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import vn.com.libertime.port.um.required.AppConfig
import vn.com.libertime.port.um.required.CachingClusterConfig
import vn.com.libertime.port.um.required.EnvironmentProvidable
import vn.com.libertime.port.um.required.KafkaConfig
import java.util.*

public class EnvironmentProvider : EnvironmentProvidable {

    private val environment: String by lazy {
        System.getenv()["ENVIRONMENT"] ?: defaultEnvironment
    }

    private val config: Config by lazy {
        ConfigFactory.load("${deployEnvironment}_env.conf")
    }

    override val deployEnvironment: String
        get() = environment

    override val appConfig: AppConfig
        get() = AppConfig(hashKey = config.getConfig("app.config").getString("hash.key"))

    override val cachingClusterConfig: CachingClusterConfig
        get() = CachingClusterConfig(
            host = config.getConfig("cache.redis").getString("host"),
            port = config.getConfig("cache.redis").getInt("port"),
            secretKey = config.getConfig("cache.redis").getString("secret")
        )

    override val databaseConfig: Properties
        get() = config.getConfig("database.hikari").toProperties()

    override val consumerKafkaConfig: KafkaConfig
        get() = KafkaConfig(
            bootstrapServers = config.getConfig("kafka-consumer").getString("bootstrapServers"),
            schemaUrl = config.getConfig("kafka-consumer").getString("schemaUrl")
        )

    override val producerKafkaConfig: KafkaConfig
        get() = KafkaConfig(
            bootstrapServers = config.getConfig("kafka-producer").getString("bootstrapServers"),
            schemaUrl = config.getConfig("kafka-consumer").getString("schemaUrl")
        )
}