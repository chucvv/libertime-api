package vn.com.libertime.um.data.model

import org.jetbrains.exposed.sql.Table
import java.util.*

object Users : Table() {
    val userId = long("id")
    val username = varchar("username", length = 50)
    val password = varchar("password", length = 100)
    val email = varchar("email", length = 50).nullable()
    val sex = bool("sex").default(true)
    val age = integer("age").default(0)
    val createdDate = long("createdDate").default(Date().time)
    var lastLoginDate = long("lastLoginDate").default(Date().time)

    override val primaryKey: PrimaryKey = PrimaryKey(userId, name = "PK_UserID")
}