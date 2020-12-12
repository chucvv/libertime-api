package vn.com.libertime.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import vn.com.libertime.database.table.UserProfiles
import vn.com.libertime.database.table.Users
import javax.sql.DataSource

public class Database(config: DataSource) {

    private val tables = arrayOf(Users, UserProfiles)

    init {
        val db = connect(config)
        transaction(db) {
            SchemaUtils.createMissingTablesAndColumns(*tables)
        }
    }

    private fun connect(database: DataSource): Database {
        return Database.connect(database)
    }
}