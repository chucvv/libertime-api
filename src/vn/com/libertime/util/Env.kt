package vn.com.libertime.util

import vn.com.libertime.productionEnvironment

fun isProduction(environment: String): Boolean = environment == productionEnvironment