package vn.com.libertime.domain.um

import vn.com.libertime.common.Result
import vn.com.libertime.common.UseCase
import vn.com.libertime.port.provided.entity.UserInfo
import vn.com.libertime.port.required.UserRepository

class GetUserByIdUseCase(private val userService: UserRepository) : UseCase<String, UserInfo> {
    override suspend operator fun invoke(userId: String): Result<UserInfo> {
        val userEntity = userService.getUserById(userId) ?: return Result.Error.BusinessException("User not found")
        val userProfileEntity = userService.getUserProfileById(userId)
        return Result.Success(UserInfo(userEntity, userProfileEntity))
    }
}