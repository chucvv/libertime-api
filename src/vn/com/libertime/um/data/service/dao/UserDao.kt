package vn.com.libertime.um.data.service.dao

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import vn.com.libertime.shared.functions.library.exception.DatabaseException
import vn.com.libertime.shared.functions.library.extension.toUserEntity
import vn.com.libertime.shared.functions.library.extension.toUserProfileEntity
import vn.com.libertime.um.data.model.UserProfile
import vn.com.libertime.um.data.model.Users
import vn.com.libertime.um.domain.entity.UserEntity
import vn.com.libertime.um.domain.entity.UserInfoEntity
import vn.com.libertime.um.domain.entity.UserProfileEntity
import vn.com.libertime.um.domain.usecase.RegisterParam
import vn.com.libertime.um.domain.usecase.UpdateUserParam

data class UpdateUserDaoParam(
    val email: String,
    val firstname: String,
    val lastname: String,
    val sex: Boolean,
    val birthday: Long,
    val firebaseId: String?,
    val address: String,
    val university: String,
    val lat: Double?,
    val lng: Double?,
) {
    companion object {
        fun fromUpdateUserParam(updateUserParam: UpdateUserParam): UpdateUserDaoParam = UpdateUserDaoParam(
            email = updateUserParam.email,
            firstname = updateUserParam.firstname,
            lastname = updateUserParam.lastname,
            sex = updateUserParam.sex,
            birthday = updateUserParam.birthday,
            firebaseId = updateUserParam.firebaseId,
            address = updateUserParam.address,
            university = updateUserParam.university,
            lat = updateUserParam.lat,
            lng = updateUserParam.lng
        )
    }
}

data class CreateUserDaoParam(val username: String, val password: String, val email: String?) {
    companion object {
        fun fromRegisterParam(registerParam: RegisterParam): CreateUserDaoParam = CreateUserDaoParam(
            username = registerParam.userName,
            password = registerParam.password,
            email = registerParam.email
        )
    }
}

interface UserDao {
    suspend fun createUser(userid: Long, registerParamEntity: CreateUserDaoParam): UserEntity?
    suspend fun updateUser(userid: Long, updateUserParam: UpdateUserDaoParam): UserInfoEntity?
    suspend fun getUserByName(username: String): UserEntity?
    suspend fun getUserById(userid: Long): UserEntity?
    suspend fun getUserProfileById(userid: Long): UserProfileEntity?
}

class DefaultUserDao : UserDao {

    override suspend fun createUser(userid: Long, registerParamEntity: CreateUserDaoParam): UserEntity? {
        return try {
            transaction {
                Users.insert {
                    it[id] = userid
                    it[username] = registerParamEntity.username
                    it[password] = registerParamEntity.password
                    it[email] = registerParamEntity.email
                }
                UserProfile.insert {
                    it[userId] = userid
                }
                Users.select { Users.id eq userid }.singleOrNull()?.toUserEntity()
            }
        } catch (ex: Exception) {
            throw DatabaseException(ex)
        }
    }

    override suspend fun updateUser(userid: Long, updateUserParam: UpdateUserDaoParam): UserInfoEntity? {
        return try {
            transaction {
                Users.update({ Users.id eq userid }) {
                    it[email] = updateUserParam.email
                    it[firstname] = updateUserParam.firstname
                    it[lastname] = updateUserParam.lastname
                    it[sex] = updateUserParam.sex
                    it[birthday] = updateUserParam.birthday
                }
                UserProfile.update({ UserProfile.userId eq userid }) {
                    it[firebaseId] = updateUserParam.firebaseId
                    it[address] = updateUserParam.address
                    it[university] = updateUserParam.university
                    it[lat] = updateUserParam.lat
                    it[lng] = updateUserParam.lng
                }
                val user = Users.select { Users.id eq userid }.single().toUserEntity()
                val userProfile =
                    UserProfile.select { UserProfile.userId eq userid }.singleOrNull()?.toUserProfileEntity()
                UserInfoEntity(user, userProfile)
            }
        } catch (ex: Exception) {
            throw DatabaseException(ex)
        }
    }

    override suspend fun getUserByName(username: String): UserEntity? = transaction {
        Users.select { Users.username eq username }.singleOrNull()?.toUserEntity()
    }

    override suspend fun getUserById(userid: Long): UserEntity? = transaction {
        Users.select { Users.id eq userid }.singleOrNull()?.toUserEntity()
    }

    override suspend fun getUserProfileById(userid: Long): UserProfileEntity? = transaction {
        UserProfile.select { UserProfile.userId eq userid }.singleOrNull()?.toUserProfileEntity()
    }
}