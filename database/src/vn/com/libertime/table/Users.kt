package vn.com.libertime.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime

object Users : UUIDTable() {
    val username = varchar("username", length = 100)
    val password = varchar("password", length = 100)
    val firstname = varchar("firstname", length = 50).nullable()
    val lastname = varchar("lastname", length = 50).nullable()
    val email = varchar("email", length = 50).nullable()
    val sex = bool("sex").default(true)
    val birthday = long("birthday").default(0)
    val createdDate = datetime("createdDate").default(DateTime.now())

    override val primaryKey: PrimaryKey = PrimaryKey(id, name = "PK_Users_ID")
}