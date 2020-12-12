package vn.com.libertime.common.env

internal const val defaultEnvironment = "sandbox"
internal const val productionEnvironment = "production"

internal fun isProduction(environment: String): Boolean = environment == productionEnvironment