package vn.com.libertime.infrastructure

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import vn.com.libertime.common.env.defaultEnvironment
import vn.com.libertime.port.um.required.CachingClusterConfig
import vn.com.libertime.port.um.required.EnvironmentProvidable

public class EnvironmentProvider : EnvironmentProvidable {

    private val environment: String by lazy {
        System.getenv()["ENVIRONMENT"] ?: defaultEnvironment
    }

    private val config: Config by lazy {
        ConfigFactory.load("${deployEnvironment}_env.conf")
    }

    override val deployEnvironment: String
        get() = environment

    override val cachingClusterConfig: CachingClusterConfig
        get() = CachingClusterConfig(
            host = config.getConfig("redis").getString("host"),
            port = config.getConfig("redis").getInt("port"),
            secretKey = config.getConfig("redis").getString("secret")
        )
}