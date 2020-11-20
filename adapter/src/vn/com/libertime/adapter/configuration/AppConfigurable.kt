package vn.com.libertime.adapter.configuration

import io.ktor.application.*

interface AppConfigurable {
    fun apply(application: Application)
}