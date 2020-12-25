package vn.com.libertime.adapter.server_side.log

import mu.KotlinLogging
import vn.com.libertime.port.um.required.Logger

public class DefaultLogger(private val logger: KotlinLogging) : Logger {
    override fun info(message: String): Unit = logger.logger(this.toString()).info(message)

    override fun error(message: String, exception: Exception): Unit = logger.logger(this.toString()).catching(exception)
}