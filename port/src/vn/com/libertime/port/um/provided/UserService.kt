package vn.com.libertime.port.um.provided

import vn.com.libertime.common.Result
import vn.com.libertime.port.um.entity.UserInfo
import vn.com.libertime.port.um.required.UpdateUserParam

interface UserService {
    suspend fun getUser(userId: String): Result<UserInfo>
    suspend fun updateUser(params: UpdateUserParam): Result<String>
}