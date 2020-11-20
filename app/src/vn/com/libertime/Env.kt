package vn.com.libertime

const val defaultEnvironment = "sandbox"
const val productionEnvironment = "production"

fun isProduction(environment: String): Boolean = environment == productionEnvironment