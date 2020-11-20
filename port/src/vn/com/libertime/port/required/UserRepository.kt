package vn.com.libertime.port.required

import vn.com.libertime.port.provided.entity.User
import vn.com.libertime.port.provided.entity.UserProfile
import vn.com.libertime.port.provided.user_auth.RegisterParam

data class UpdateUserParam(
    val userId: String,
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

interface UserRepository {
    suspend fun createUser(registerParam: RegisterParam): String
    suspend fun updateUser(updateUserParam: UpdateUserParam): String
    suspend fun getUserByName(username: String): User?
    suspend fun getUserById(userid: String): User?
    suspend fun getUserProfileById(userid: String): UserProfile?
}