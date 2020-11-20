package vn.com.libertime.dao

import org.jetbrains.exposed.sql.transactions.transaction
import vn.com.libertime.DatabaseException
import vn.com.libertime.entity.EntityUser
import vn.com.libertime.entity.EntityUserProfile
import vn.com.libertime.table.UserProfiles
import vn.com.libertime.table.Users
import java.util.*

class DefaultUserDao : UserDao {

    override suspend fun createUser(registerParamEntity: CreateUserDaoParam): String =
        runCatching {
            transaction {
                EntityUser.new {
                    username = registerParamEntity.username
                    password = registerParamEntity.password
                }.let {
                    EntityUserProfile.new {
                        user = EntityUser[it.id]
                    }.user.id.value.toString()
                }
            }
        }.getOrElse { throw DatabaseException(it) }


    override suspend fun updateUser(userid: String, updateUserParam: UpdateUserDaoParam): String =
        runCatching {
            transaction {
                EntityUserProfile.find { (UserProfiles.user eq UUID.fromString(userid)) }.firstOrNull()?.apply {
                    firebaseId = updateUserParam.firebaseId
                    address = updateUserParam.address
                    university = updateUserParam.university
                    lat = updateUserParam.lat
                    lng = updateUserParam.lng
                }
                EntityUser[UUID.fromString(userid)].apply {
                    email = updateUserParam.email
                    firstname = updateUserParam.firstname
                    lastname = updateUserParam.lastname
                    sex = updateUserParam.sex
                    birthday = updateUserParam.birthday
                }.id.value.toString()
            }
        }.getOrElse { throw DatabaseException(it) }

    override suspend fun getUserByName(username: String): EntityUser? = transaction {
        EntityUser.find { (Users.username eq username) }.firstOrNull()
    }

    override suspend fun getUserById(userId: String): EntityUser? = transaction {
        EntityUser.findById(UUID.fromString(userId))
    }

    override suspend fun getUserProfileById(userId: String): EntityUserProfile? = transaction {
        EntityUserProfile.find { (UserProfiles.user eq UUID.fromString(userId)) }.firstOrNull()
    }
}