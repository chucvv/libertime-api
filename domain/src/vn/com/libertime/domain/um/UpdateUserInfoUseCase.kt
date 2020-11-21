package vn.com.libertime.domain.um

import vn.com.libertime.common.Result
import vn.com.libertime.common.UseCase
import vn.com.libertime.port.um.required.UpdateUserParam
import vn.com.libertime.port.um.required.UserRepository

internal class UpdateUserInfoUseCase(private val userService: UserRepository) : UseCase<UpdateUserParam, String> {
    override suspend operator fun invoke(params: UpdateUserParam): Result<String> = runCatching {
        userService.getUserById(params.userId) ?: return Result.Error.BusinessException("User is not found")
        val userId = userService.updateUser(
            params
        )
        Result.Success(userId)
    }.getOrElse {
        Result.Error.InternalSystemException(it.message)
    }
}