package vn.com.libertime.domain.um

import vn.com.libertime.common.Result
import vn.com.libertime.common.UseCase
import vn.com.libertime.port.um.required.UserDao
import vn.com.libertime.port.um.required.UserUpdateParam

internal class UpdateUserInfoUseCase(private val userDao: UserDao) : UseCase<UserUpdateParam, String> {
    override suspend operator fun invoke(params: UserUpdateParam): Result<String> = runCatching {
        userDao.getUserById(params.userId) ?: return Result.Error.BusinessException("User is not found")
        val userId = userDao.updateUser(params)
        Result.Success(userId)
    }.getOrElse {
        Result.Error.InternalSystemException(it.message)
    }
}