package vn.com.libertime.shared.functions.library

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

fun initDB(environment: String) {
    val config = HikariConfig("/${environment}_hikari.properties")
    config.schema = "public"
    config.maximumPoolSize = 3
    config.isAutoCommit = false
    config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    config.validate()
    val ds = HikariDataSource(config)
    Database.connect(ds)
}