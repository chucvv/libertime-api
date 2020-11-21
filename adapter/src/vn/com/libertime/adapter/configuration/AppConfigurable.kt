package vn.com.libertime.adapter.configuration

import io.ktor.application.*

public interface AppConfigurable {
    public fun apply(application: Application)
}