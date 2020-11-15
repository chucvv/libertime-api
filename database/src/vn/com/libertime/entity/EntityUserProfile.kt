package vn.com.libertime.entity

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import vn.com.libertime.table.UserProfiles
import java.util.*

class EntityUserProfile(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<EntityUserProfile>(UserProfiles)

    var user by EntityUser referencedOn UserProfiles.user
    var firebaseId by UserProfiles.firebaseId
    var address by UserProfiles.address
    var university by UserProfiles.university
    var lat by UserProfiles.lat
    var lng by UserProfiles.lng
    val lastLoginDate by UserProfiles.lastLoginDate
}