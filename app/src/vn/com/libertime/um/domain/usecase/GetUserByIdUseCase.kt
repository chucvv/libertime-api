package vn.com.libertime.um.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.um.domain.entity.UserInfoEntity
import vn.com.libertime.um.domain.service.UserService

@KoinApiExtension
class GetUserByIdUseCase : UseCase<Long, UserInfoEntity> {
    private val userService by inject<UserService>()
    override suspend operator fun invoke(userId: Long): Result<UserInfoEntity> {
        val userEntity = userService.getUserById(userId) ?: return Result.Error.BusinessException("User not found")
        val userProfileEntity = userService.getUserProfileById(userId)
        return Result.Success(UserInfoEntity(userEntity, userProfileEntity))
    }
}