package vn.com.libertime.adapter.server_side.cache

import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import io.lettuce.core.api.StatefulRedisConnection

class Redis constructor(host: String, port: Int, secret: String) {
    init {
        val redisURI = RedisURI.builder().withHost(host).withPort(port).withPassword(secret).withDatabase(1).build()
        val redisClient = RedisClient.create(redisURI)
        val connection: StatefulRedisConnection<String, String> = redisClient.connect()
    }
}