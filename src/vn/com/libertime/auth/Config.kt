package vn.com.libertime.auth

import io.ktor.config.*
import io.ktor.util.*

/**
 * Class containing Configuration values or secret key which will be provided from
 * application.conf (from environment variables).
 */
@Suppress("PropertyName")
@KtorExperimentalAPI
class Config(config: ApplicationConfig) {
    val SECRET_KEY = config.property("key.secret").getString()
}