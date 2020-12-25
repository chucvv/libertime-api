package vn.com.libertime.adapter.client_side.um.controller

import vn.com.libertime.adapter.client_side.um.response.UserSearchInRadiusResponse
import vn.com.libertime.common.Result
import vn.com.libertime.common.extension.exhaustive
import vn.com.libertime.common.takeException
import vn.com.libertime.port.um.provided.UserLocationService

public class UserLocationController(private val userLocationService: UserLocationService) {
    public suspend fun searchInRadius(userId: String, radius: Double): UserSearchInRadiusResponse =
        when (val result = userLocationService.searchInRadius(userId, radius)) {
            is Result.Success -> UserSearchInRadiusResponse.success(result.data)
            is Result.Error.InternalSystemException -> UserSearchInRadiusResponse.failed(result.takeException())
            is Result.Error.BusinessException -> UserSearchInRadiusResponse.notFound(result.takeException())
        }.exhaustive
}