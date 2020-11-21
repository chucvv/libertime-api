package vn.com.libertime.adapter.configuration

import io.ktor.application.*

public class ConfigCenter : AppConfigurable {
    private val configs: MutableList<AppConfigurable> = ArrayList()

    public fun register(appConfigurable: AppConfigurable): ConfigCenter = apply { configs.add(appConfigurable) }

    override fun apply(application: Application): Unit = configs.forEach {
        it.apply(application)
    }
}