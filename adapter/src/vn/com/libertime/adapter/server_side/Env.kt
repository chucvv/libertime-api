package vn.com.libertime.adapter.server_side

internal const val defaultEnvironment = "sandbox"
internal const val productionEnvironment = "production"

internal fun isProduction(environment: String): Boolean = environment == productionEnvironment