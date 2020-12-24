package vn.com.libertime.domain.um

import vn.com.libertime.common.Result
import vn.com.libertime.common.UseCase
import vn.com.libertime.port.um.entity.UserInfo
import vn.com.libertime.port.um.required.UserRepository

internal class GetUserByIdUseCase(private val userDao: UserRepository) : UseCase<String, UserInfo> {
    override suspend operator fun invoke(userId: String): Result<UserInfo> {
        val userEntity = userDao.getUserById(userId) ?: return Result.Error.BusinessException("User not found")
        val userProfileEntity = userDao.getUserProfileById(userId)
        return Result.Success(UserInfo(userEntity, userProfileEntity))
    }
}