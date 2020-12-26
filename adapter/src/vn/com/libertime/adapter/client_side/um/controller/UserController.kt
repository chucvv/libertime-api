package vn.com.libertime.adapter.client_side.um.controller

import vn.com.libertime.adapter.client_side.um.response.MeResponse
import vn.com.libertime.adapter.client_side.um.response.UserProfileResponse
import vn.com.libertime.common.Result
import vn.com.libertime.common.extension.exhaustive
import vn.com.libertime.common.takeException
import vn.com.libertime.port.um.provided.UserService
import vn.com.libertime.port.um.required.UserUpdateParam

public class UserController(private val userService: UserService) {

    public suspend fun getProfile(userId: String): UserProfileResponse =
        when (val result = userService.getUser(userId)) {
            is Result.Success -> UserProfileResponse.success(MeResponse.fromUserInfoEntity(result.data))
            is Result.Error.InternalSystemException -> UserProfileResponse.failed(result.takeException())
            is Result.Error.BusinessException -> UserProfileResponse.notFound(result.takeException())
        }.exhaustive

    public suspend fun updateProfile(updateUserParam: UserUpdateParam): UserProfileResponse =
        when (val result = userService.updateUser(updateUserParam)) {
            is Result.Success -> UserProfileResponse.success(null)
            is Result.Error.InternalSystemException -> UserProfileResponse.failed(result.takeException())
            is Result.Error.BusinessException -> UserProfileResponse.notFound(result.takeException())
        }.exhaustive
}