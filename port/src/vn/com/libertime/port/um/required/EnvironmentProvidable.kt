package vn.com.libertime.port.um.required

import java.util.*

public data class CachingClusterConfig(val host: String, val port: Int, val secretKey: String)

public data class KafkaConfig(val bootstrapServers: String, val schemaUrl: String)

public data class AppConfig(val hashKey: String)

public interface EnvironmentProvidable {
    public val deployEnvironment: String
    public val appConfig: AppConfig
    public val cachingClusterConfig: CachingClusterConfig
    public val databaseConfig: Properties
    public val consumerKafkaConfig: KafkaConfig
    public val producerKafkaConfig: KafkaConfig
}