package vn.com.libertime.domain.um

import vn.com.libertime.common.Result
import vn.com.libertime.port.um.entity.UserInfo
import vn.com.libertime.port.um.provided.UserService
import vn.com.libertime.port.um.required.UpdateUserParam

internal class DefaultUserService(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) : UserService {
    override suspend fun getUser(userId: String): Result<UserInfo> = getUserByIdUseCase(userId)

    override suspend fun updateUser(params: UpdateUserParam): Result<String> = updateUserInfoUseCase(params)

}