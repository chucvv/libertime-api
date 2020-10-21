package vn.com.libertime.um.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.um.domain.entity.UserInfoEntity
import vn.com.libertime.um.domain.service.UpdateUserDaoParam
import vn.com.libertime.um.domain.service.UserService

data class UpdateUserParam(val userId: Long, val username: String?, val email: String?)

@KoinApiExtension
class UpdateUserInfoUseCase : UseCase<UpdateUserParam, UserInfoEntity> {
    private val userService by inject<UserService>()
    override suspend operator fun invoke(params: UpdateUserParam): Result<UserInfoEntity> {
        try {
            val userId = params.userId
            userService.getUserById(userId) ?: Result.Error.BusinessException("User is not found")
            val user: UserInfoEntity = userService.updateUser(
                userId,
                UpdateUserDaoParam(userName = params.username, email = params.email)
            ) ?: return Result.Error.BusinessException("User updated unsuccessfully")
            return Result.Success(user)
        } catch (ex: Exception) {
            return Result.Error.InternalSystemException(ex.message)
        }
    }
}