package vn.com.libertime.port.um.required

public data class CachingClusterConfig(val host: String, val port: Int, val secretKey: String)

public interface EnvironmentProvidable {
    public val deployEnvironment: String
    public val cachingClusterConfig: CachingClusterConfig
}