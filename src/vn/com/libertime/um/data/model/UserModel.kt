package vn.com.libertime.um.data.model

import vn.com.libertime.um.domain.entity.UserInfoEntity
import java.util.*

data class UserModel(
    val id: Long,
    val username: String,
    val password: String,
    val email: String?,
    val createdDate: Long = Date().time,
    val lastLoginDate: Long = Date().time
) {
    fun toUserInfoEntity(): UserInfoEntity = UserInfoEntity(id, username, password, email, createdDate, lastLoginDate)
}