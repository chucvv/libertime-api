package vn.com.libertime.adapter.user_management.controller

import vn.com.libertime.adapter.user_management.response.AuthResponse
import vn.com.libertime.common.Result
import vn.com.libertime.common.extension.exhaustive
import vn.com.libertime.common.takeException
import vn.com.libertime.port.provided.user_auth.AuthService
import vn.com.libertime.port.provided.user_auth.LoginParam
import vn.com.libertime.port.provided.user_auth.RegisterParam

interface AuthController {
    suspend fun login(username: String, password: String): AuthResponse
    suspend fun register(username: String, password: String): AuthResponse
}

class DefaultAuthController(private val authService: AuthService) : AuthController {

    override suspend fun login(username: String, password: String): AuthResponse = when (val result =
        authService.login(LoginParam(username = username, password = password))) {
        is Result.Success -> AuthResponse.success(result.data)
        is Result.Error.InternalSystemException -> AuthResponse.failed(result.takeException())
        is Result.Error.BusinessException -> AuthResponse.unauthorized(result.takeException())
    }.exhaustive

    override suspend fun register(username: String, password: String): AuthResponse =
        when (val result = authService.register(
            RegisterParam(
                userName = username,
                password = password,
            )
        )) {
            is Result.Success -> AuthResponse.success(result.data)
            is Result.Error.InternalSystemException -> AuthResponse.failed(result.takeException())
            is Result.Error.BusinessException -> AuthResponse.failed(result.takeException())
        }.exhaustive

}