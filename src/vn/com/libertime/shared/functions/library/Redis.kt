package vn.com.libertime.shared.functions.library

import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import io.lettuce.core.api.StatefulRedisConnection

fun initRedis(environment: String) {
    val redisURI = RedisURI.builder().withHost("localhost").withPort(6379).withDatabase(1).build()
    val redisClient = RedisClient.create(redisURI)
    val connection: StatefulRedisConnection<String, String> = redisClient.connect()
}