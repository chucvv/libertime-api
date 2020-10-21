package vn.com.libertime.workspace.data.model

import org.jetbrains.exposed.sql.Table
import vn.com.libertime.um.data.model.Users
import java.util.*

object Workspaces : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", length = 100)
    val createdDate = long("createdDate").default(Date().time)
    val userId = (long("userId") references Users.userId).nullable()

    override val primaryKey: PrimaryKey = PrimaryKey(id, name = "PK_Workspaces_ID")
}