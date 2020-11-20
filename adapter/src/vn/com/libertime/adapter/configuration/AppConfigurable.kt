package vn.com.libertime.adapter.configuration

import io.ktor.auth.*

interface AppConfigurable {
    fun apply(configurable: Authentication.Configuration)
}