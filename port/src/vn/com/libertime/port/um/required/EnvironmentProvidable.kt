package vn.com.libertime.port.um.required

import java.util.*

public data class CachingClusterConfig(val host: String, val port: Int, val secretKey: String)

public data class AppConfig(val hashKey: String)

public interface EnvironmentProvidable {
    public val deployEnvironment: String
    public val appConfig: AppConfig
    public val cachingClusterConfig: CachingClusterConfig
    public val databaseConfig: Properties
}