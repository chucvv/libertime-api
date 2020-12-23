package vn.com.libertime.port.um.required

import vn.com.libertime.port.um.entity.User
import vn.com.libertime.port.um.entity.UserProfile

public data class UserUpdateParam(
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

public data class UserRegisterParam(val username: String, val password: String)

public interface UserDao {
    public suspend fun createUser(params: UserRegisterParam): User
    public suspend fun updateUser(params: UserUpdateParam): String
    public suspend fun getUserByName(username: String): User?
    public suspend fun getUserById(userId: String): User?
    public suspend fun getUserProfileById(userId: String): UserProfile?
}