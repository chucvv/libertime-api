package vn.com.libertime.um.data.model

import org.jetbrains.exposed.sql.Table
import java.util.*

object UserProfile : Table() {
    val id = long("id").autoIncrement()
    val userId = (long("userId") references Users.id)
    val firebaseId = varchar("firebaseId", length = 100).nullable()
    val address = varchar("address", length = 100).nullable()
    val university = varchar("university", length = 100).nullable()
    val lat = double("lat").nullable()
    val lng = double("lng").nullable()
    val lastLoginDate = long("lastLoginDate").default(Date().time)

    override val primaryKey: PrimaryKey = PrimaryKey(id, name = "PK_UserProfiles_ID")
}