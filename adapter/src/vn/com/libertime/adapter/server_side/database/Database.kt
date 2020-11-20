package vn.com.libertime.adapter.server_side.database

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
        val db = connect()
        transaction(db) {
            SchemaUtils.createMissingTablesAndColumns(*tables)
        }
    }

    private fun connect(): Database {
        val config = HikariConfig("/${environment}_hikari.properties").apply {
            maximumPoolSize = 3
            connectionTimeout = 30000
            leakDetectionThreshold = 2000
            validate()
        }
        val ds = HikariDataSource(config)
        return Database.connect(ds)
    }
}