package vn.com.libertime.domain.um

import vn.com.libertime.common.Result
import vn.com.libertime.port.provided.entity.UserInfo
import vn.com.libertime.port.provided.user_auth.UserService
import vn.com.libertime.port.required.UpdateUserParam

class DefaultUserService(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) : UserService {
    override suspend fun getUserByUserId(userId: String): Result<UserInfo> = getUserByIdUseCase(userId)

    override suspend fun updateUser(params: UpdateUserParam): Result<String> = updateUserInfoUseCase(params)

}