package vn.com.libertime.shared.functions.library

import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import io.lettuce.core.api.StatefulRedisConnection

class Redis constructor(host: String, port: Int) {
    init {
        val redisURI = RedisURI.builder().withHost(host).withPort(port).withDatabase(1).build()
        val redisClient = RedisClient.create(redisURI)
        val connection: StatefulRedisConnection<String, String> = redisClient.connect()
    }
}