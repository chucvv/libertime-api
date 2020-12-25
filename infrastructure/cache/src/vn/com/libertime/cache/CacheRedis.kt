package vn.com.libertime.cache

import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import io.lettuce.core.api.StatefulRedisConnection
import vn.com.libertime.port.um.required.Cacheable
import java.util.concurrent.TimeUnit

public class CacheRedis constructor(host: String, port: Int, secret: String) : Cacheable {
    private lateinit var connection: StatefulRedisConnection<String, String>
    private val asyncCommands by lazy {
        connection.async()
    }

    init {
        val redisURI = RedisURI.builder()
            .withHost(host)
            .withPort(port)
            .withPassword(secret)
            .withDatabase(1)
            .build()

        val redisClient = RedisClient.create(redisURI)
        connection = redisClient.connect()
    }

    override suspend fun put(key: String, value: String) {
        asyncCommands.append(key, value)
    }

    override suspend fun get(key: String): String? =
        try {
            val valueFuture = asyncCommands.get(key)
            valueFuture.get(1, TimeUnit.SECONDS)
        } catch (ex: Exception) {
            ""
        }
}