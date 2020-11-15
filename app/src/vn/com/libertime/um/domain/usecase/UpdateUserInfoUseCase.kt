package vn.com.libertime.um.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.um.domain.service.UserService

data class UpdateUserParam(
    val userId: String,
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
class UpdateUserInfoUseCase : UseCase<UpdateUserParam, String> {
    private val userService by inject<UserService>()
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