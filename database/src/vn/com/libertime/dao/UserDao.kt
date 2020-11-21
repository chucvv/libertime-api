package vn.com.libertime.dao

import vn.com.libertime.entity.EntityUser
import vn.com.libertime.entity.EntityUserProfile

public data class UpdateUserDaoParam(
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

public data class CreateUserDaoParam(val username: String, val password: String)

public interface UserDao {
    public suspend fun createUser(registerParamEntity: CreateUserDaoParam): String
    public suspend fun updateUser(userid: String, updateUserParam: UpdateUserDaoParam): String
    public suspend fun getUserByName(username: String): EntityUser?
    public suspend fun getUserById(userId: String): EntityUser?
    public suspend fun getUserProfileById(userId: String): EntityUserProfile?
}