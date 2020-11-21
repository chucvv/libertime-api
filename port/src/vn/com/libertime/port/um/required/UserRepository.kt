package vn.com.libertime.port.um.required

import vn.com.libertime.port.um.entity.User
import vn.com.libertime.port.um.entity.UserProfile

public data class UpdateUserParam(
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

public data class RegisterParam(val userName: String, val password: String)

public interface UserRepository {
    public suspend fun createUser(registerParam: RegisterParam): String
    public suspend fun updateUser(updateUserParam: UpdateUserParam): String
    public suspend fun getUserByName(username: String): User?
    public suspend fun getUserById(userid: String): User?
    public suspend fun getUserProfileById(userid: String): UserProfile?
}