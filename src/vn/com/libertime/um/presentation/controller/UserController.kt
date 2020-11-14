package vn.com.libertime.um.presentation.controller

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.extension.exhaustive
import vn.com.libertime.shared.functions.library.takeException
import vn.com.libertime.um.domain.usecase.GetUserByIdUseCase
import vn.com.libertime.um.domain.usecase.UpdateUserInfoUseCase
import vn.com.libertime.um.domain.usecase.UpdateUserParam
import vn.com.libertime.um.presentation.model.response.MeResponse
import vn.com.libertime.um.presentation.model.response.UserProfileResponse

interface UserController {
    suspend fun getProfile(userId: Long): UserProfileResponse
    suspend fun updateProfile(updateUserParam: UpdateUserParam): UserProfileResponse
}

@KoinApiExtension
class DefaultUserController : UserController, KoinComponent {

    val updateUserInfoUseCase by inject<UpdateUserInfoUseCase>()
    val getUserByIdUseCase by inject<GetUserByIdUseCase>()

    override suspend fun getProfile(userId: Long): UserProfileResponse =
        when (val result = getUserByIdUseCase(userId)) {
            is Result.Success -> UserProfileResponse.success(MeResponse.fromUserInfoEntity(result.data))
            is Result.Error.InternalSystemException -> UserProfileResponse.failed(result.takeException())
            is Result.Error.BusinessException -> UserProfileResponse.notFound(result.takeException())
        }.exhaustive

    override suspend fun updateProfile(updateUserParam: UpdateUserParam): UserProfileResponse =
        when (val result = updateUserInfoUseCase(updateUserParam)) {
            is Result.Success -> UserProfileResponse.success(MeResponse.fromUserInfoEntity(result.data))
            is Result.Error.InternalSystemException -> UserProfileResponse.failed(result.takeException())
            is Result.Error.BusinessException -> UserProfileResponse.notFound(result.takeException())
        }.exhaustive
}