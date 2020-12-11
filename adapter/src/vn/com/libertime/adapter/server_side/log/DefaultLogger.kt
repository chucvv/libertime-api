package vn.com.libertime.common.log

import mu.KotlinLogging

public class DefaultLogger(private val logger: KotlinLogging) : Logger {
    override fun info(message: String): Unit = logger.logger(this.toString()).info(message)

    override fun error(message: String, exception: Exception): Unit = logger.logger(this.toString()).catching(exception)
}