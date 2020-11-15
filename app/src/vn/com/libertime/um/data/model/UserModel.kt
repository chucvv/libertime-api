package vn.com.libertime.um.data.model

import org.jetbrains.exposed.sql.Table
import java.util.*

object Users : Table() {
    val id = long("id")
    val username = varchar("username", length = 50)
    val password = varchar("password", length = 100)
    val firstname = varchar("firstname", length = 50).nullable()
    val lastname = varchar("lastname", length = 50).nullable()
    val email = varchar("email", length = 50).nullable()
    val sex = bool("sex").default(true)
    val birthday = long("birthday").default(0)
    val createdDate = long("createdDate").default(Date().time)

    override val primaryKey: PrimaryKey = PrimaryKey(id, name = "PK_Users_ID")
}