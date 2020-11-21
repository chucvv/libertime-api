package vn.com.libertime.port.um.provided

import vn.com.libertime.common.Result
import vn.com.libertime.port.um.entity.UserInfo
import vn.com.libertime.port.um.required.UpdateUserParam

public interface UserService {
    public suspend fun getUser(userId: String): Result<UserInfo>
    public suspend fun updateUser(params: UpdateUserParam): Result<String>
}