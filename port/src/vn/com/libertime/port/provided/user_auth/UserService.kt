package vn.com.libertime.port.provided.user_auth

import vn.com.libertime.common.Result
import vn.com.libertime.port.provided.entity.UserInfo
import vn.com.libertime.port.required.UpdateUserParam

interface UserService {
    suspend fun getUserByUserId(userId: String): Result<UserInfo>
    suspend fun updateUser(params: UpdateUserParam): Result<String>
}