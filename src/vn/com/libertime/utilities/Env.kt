package vn.com.libertime.utilities

import vn.com.libertime.application.productionEnvironment

fun isProduction(environment: String): Boolean = environment == productionEnvironment