package vn.com.libertime.port.um.required

import vn.com.libertime.port.um.entity.User
import vn.com.libertime.port.um.entity.UserProfile

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

data class RegisterParam(val userName: String, val password: String)

interface UserRepository {
    suspend fun createUser(registerParam: RegisterParam): String
    suspend fun updateUser(updateUserParam: UpdateUserParam): String
    suspend fun getUserByName(username: String): User?
    suspend fun getUserById(userid: String): User?
    suspend fun getUserProfileById(userid: String): UserProfile?
}