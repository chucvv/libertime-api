package vn.com.libertime.um.data.model

import vn.com.libertime.um.domain.entity.UserEntity

data class UserModel(
    val id: String,
    val userName: String,
    val password: String,
    val email: String,
    val createdDate: Long
) {
    fun toUserEntity(): UserEntity =
        UserEntity(userId = id, userName = userName, password = password, email = email)
}