package vn.com.libertime

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import vn.com.libertime.table.UserProfiles
import vn.com.libertime.table.Users

class Database(private val environment: String) {

    private val tables = arrayOf(Users, UserProfiles)

    init {
        connect()
        transaction {
            SchemaUtils.createMissingTablesAndColumns(*tables)
        }
    }

    private fun connect() {
        val config = HikariConfig("/${environment}_hikari.properties")
        config.schema = "public"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        val ds = HikariDataSource(config)
        Database.connect(ds)
    }
}