package vn.com.libertime.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime

internal object UserProfiles : UUIDTable() {
    val user = reference("user", Users)
    val firebaseId = varchar("firebaseId", length = 100).nullable()
    val address = varchar("address", length = 100).nullable()
    val university = varchar("university", length = 100).nullable()
    val lat = double("lat").nullable()
    val lng = double("lng").nullable()
    val lastLoginDate = datetime("lastLoginDate").default(DateTime.now())

    override val primaryKey: PrimaryKey = PrimaryKey(id, name = "PK_UserProfiles_ID")
}