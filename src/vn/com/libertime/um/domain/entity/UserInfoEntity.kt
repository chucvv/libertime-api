package vn.com.libertime.um.domain.entity

data class UserInfoEntity(
    val userId: Long,
    val userName: String,
    val password: String,
    val email: String?,
    val createdDate: Long,
    val lastLoginDate: Long
) {
    fun toUserCredentialsEntity() = UserCredentialsEntity(userId, userName)
}