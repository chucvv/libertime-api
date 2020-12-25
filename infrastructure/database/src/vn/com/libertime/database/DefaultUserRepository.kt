package vn.com.libertime.database

import org.jetbrains.exposed.sql.transactions.transaction
import org.postgis.Point
import vn.com.libertime.database.entity.EntityUser
import vn.com.libertime.database.entity.EntityUserProfile
import vn.com.libertime.database.table.UserProfiles
import vn.com.libertime.database.table.Users
import vn.com.libertime.port.um.entity.User
import vn.com.libertime.port.um.entity.UserProfile
import vn.com.libertime.port.um.required.UserRegisterParam
import vn.com.libertime.port.um.required.UserRepository
import vn.com.libertime.port.um.required.UserUpdateParam
import java.util.*

public class DefaultUserRepository : UserRepository {

    override suspend fun createUser(params: UserRegisterParam): User =
        runCatching {
            transaction {
                EntityUser.new {
                    username = params.username
                    password = params.password
                }.let {
                    EntityUserProfile.new {
                        user = EntityUser[it.id]
                    }.user.toUserEntity()
                }
            }
        }.getOrElse { throw DatabaseException(it) }


    override suspend fun updateUser(params: UserUpdateParam): String =
        runCatching {
            transaction {
                EntityUserProfile.find { (UserProfiles.user eq UUID.fromString(params.userId)) }.firstOrNull()?.apply {
                    firebaseId = params.firebaseId
                    address = params.address
                    university = params.university
                    location = Point(params.lat ?: 0.0, params.lng ?: 0.0)
                }
                EntityUser[UUID.fromString(params.userId)].apply {
                    email = params.email
                    firstname = params.firstname
                    lastname = params.lastname
                    sex = params.sex
                    birthday = params.birthday
                }.id.value.toString()
            }
        }.getOrElse { throw DatabaseException(it) }

    override suspend fun getUserByName(username: String): User? = transaction {
        EntityUser.find { (Users.username eq username) }.firstOrNull()?.toUserEntity()
    }

    override suspend fun getUserById(userId: String): User? = transaction {
        EntityUser.findById(UUID.fromString(userId))?.toUserEntity()
    }

    override suspend fun getUserProfileById(userId: String): UserProfile? = transaction {
        EntityUserProfile.find { (UserProfiles.user eq UUID.fromString(userId)) }
            .firstOrNull()?.toUserProfileEntity()
    }
}