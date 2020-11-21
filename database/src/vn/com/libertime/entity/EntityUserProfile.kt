package vn.com.libertime.entity

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.joda.time.DateTime
import vn.com.libertime.table.UserProfiles
import java.util.*

public class EntityUserProfile(id: EntityID<UUID>) : UUIDEntity(id) {
    public companion object : UUIDEntityClass<EntityUserProfile>(UserProfiles)

    public var user: EntityUser by EntityUser referencedOn UserProfiles.user
    public var firebaseId: String? by UserProfiles.firebaseId
    public var address: String? by UserProfiles.address
    public var university: String? by UserProfiles.university
    public var lat: Double? by UserProfiles.lat
    public var lng: Double? by UserProfiles.lng
    public val lastLoginDate: DateTime by UserProfiles.lastLoginDate
}