package vn.com.libertime.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import vn.com.libertime.database.table.UserProfiles
import vn.com.libertime.database.table.Users

public class Database(config: HikariConfig) {

    private val tables = arrayOf(Users, UserProfiles)

    init {
        val db = connect(config)
        transaction(db) {
            SchemaUtils.createMissingTablesAndColumns(*tables)
        }
    }

    private fun connect(config: HikariConfig): Database {
        val ds = HikariDataSource(config)
        return Database.connect(ds)
    }
}