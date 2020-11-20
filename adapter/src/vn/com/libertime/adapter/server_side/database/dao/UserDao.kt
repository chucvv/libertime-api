package vn.com.libertime.adapter.server_side.database.dao

import vn.com.libertime.entity.EntityUser
import vn.com.libertime.entity.EntityUserProfile

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
)

data class CreateUserDaoParam(val username: String, val password: String)

interface UserDao {
    suspend fun createUser(registerParamEntity: CreateUserDaoParam): String
    suspend fun updateUser(userid: String, updateUserParam: UpdateUserDaoParam): String
    suspend fun getUserByName(username: String): EntityUser?
    suspend fun getUserById(userId: String): EntityUser?
    suspend fun getUserProfileById(userId: String): EntityUserProfile?
}