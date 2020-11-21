package vn.com.libertime.entity

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.joda.time.DateTime
import vn.com.libertime.table.Users
import java.util.*

public class EntityUser(id: EntityID<UUID>) : UUIDEntity(id) {
    public companion object : UUIDEntityClass<EntityUser>(Users)

    public var username: String by Users.username
    public var password: String by Users.password
    public var firstname: String? by Users.firstname
    public var lastname: String? by Users.lastname
    public var email: String? by Users.email
    public var sex: Boolean by Users.sex
    public var birthday: Long by Users.birthday
    public val createdDate: DateTime by Users.createdDate
}