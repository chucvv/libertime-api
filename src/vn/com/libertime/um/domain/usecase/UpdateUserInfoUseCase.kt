package vn.com.libertime.um.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.um.domain.entity.UserInfoEntity
import vn.com.libertime.um.domain.service.UserService

data class UpdateUserParam(
    val userId: Long,
    val email: String,
    val firstname: String,
    val lastname: String,
    val sex: Boolean,
    val birthday: Long,
    val firebaseId: String?,
    val address: String,
    val university: String,
    val lat: Double?,
    val lng: Double?,
)

@KoinApiExtension
class UpdateUserInfoUseCase : UseCase<UpdateUserParam, UserInfoEntity> {
    private val userService by inject<UserService>()
    override suspend operator fun invoke(params: UpdateUserParam): Result<UserInfoEntity> = runCatching {
        userService.getUserById(params.userId) ?: return Result.Error.BusinessException("User is not found")
        val user = userService.updateUser(
            params
        ) ?: return Result.Error.BusinessException("User updated unsuccessfully")
        Result.Success(user)
    }.getOrElse {
        Result.Error.InternalSystemException(it.message)
    }
}