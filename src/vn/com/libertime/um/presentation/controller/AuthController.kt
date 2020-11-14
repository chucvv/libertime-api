package vn.com.libertime.um.presentation.controller

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.extension.exhaustive
import vn.com.libertime.shared.functions.library.takeException
import vn.com.libertime.um.domain.usecase.LoginParam
import vn.com.libertime.um.domain.usecase.LoginUseCase
import vn.com.libertime.um.domain.usecase.RegisterParam
import vn.com.libertime.um.domain.usecase.RegisterUseCase
import vn.com.libertime.um.presentation.model.response.AuthResponse

interface AuthController {
    suspend fun login(username: String, password: String): AuthResponse
    suspend fun register(username: String, password: String): AuthResponse
}

@KoinApiExtension
class DefaultAuthController : AuthController, KoinComponent {
    val loginUseCase by inject<LoginUseCase>()
    val registerUseCase by inject<RegisterUseCase>()

    override suspend fun login(username: String, password: String): AuthResponse = when (val result =
        loginUseCase(LoginParam(username = username, password = password))) {
        is Result.Success -> AuthResponse.success(result.data)
        is Result.Error.InternalSystemException -> AuthResponse.failed(result.takeException())
        is Result.Error.BusinessException -> AuthResponse.unauthorized(result.takeException())
    }.exhaustive

    override suspend fun register(username: String, password: String): AuthResponse =
        when (val result = registerUseCase(
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