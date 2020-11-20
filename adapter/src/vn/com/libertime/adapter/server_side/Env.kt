package vn.com.libertime.adapter.server_side

const val defaultEnvironment = "sandbox"
const val productionEnvironment = "production"

fun isProduction(environment: String): Boolean = environment == productionEnvironment