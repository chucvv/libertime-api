package vn.com.libertime.entity

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import vn.com.libertime.table.Users
import java.util.*

class EntityUser(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<EntityUser>(Users)

    var username by Users.username
    var password by Users.password
    var firstname by Users.firstname
    var lastname by Users.lastname
    var email by Users.email
    var sex by Users.sex
    var birthday by Users.birthday
    val createdDate by Users.createdDate
}