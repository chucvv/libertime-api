package vn.com.libertime.um.presentation.controller

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.core.component.KoinApiExtension
import org.koin.ktor.ext.inject
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.extension.exhaustive
import vn.com.libertime.shared.functions.library.extension.sendOk
import vn.com.libertime.shared.functions.library.takeException
import vn.com.libertime.statuspages.BusinessException
import vn.com.libertime.statuspages.SystemException
import vn.com.libertime.um.domain.usecase.LoginParam
import vn.com.libertime.um.domain.usecase.LoginUseCase
import vn.com.libertime.um.domain.usecase.RegisterParam
import vn.com.libertime.um.domain.usecase.RegisterUseCase
import vn.com.libertime.um.presentation.`object`.LoginRequest
import vn.com.libertime.um.presentation.`object`.LoginTokenResponse
import vn.com.libertime.um.presentation.`object`.RegisterRequest
import vn.com.libertime.um.presentation.`object`.RegisterResponse

@KoinApiExtension
fun Route.registrationModule() {
    //val logger: Logger = LoggerFactory.getLogger(this.javaClass.simpleName)

    val loginUseCase by inject<LoginUseCase>()
    val registerUseCase by inject<RegisterUseCase>()

    post("user") {
        val registerRequest = runCatching { call.receive<RegisterRequest>() }.getOrElse {
            throw BadRequestException("Api require username and password to process")
        }

        when (val result = registerUseCase(
            RegisterParam(
                userid = 0L,
                userName = registerRequest.username,
                password = registerRequest.password,
                email = registerRequest.email
            )
        )) {
            is Result.Success -> {
                val data = result.data
                val registerResponse =
                    RegisterResponse(id = data.userId, userName = data.username, createdDate = data.createdDate)
                sendOk(registerResponse)
            }
            is Result.Error.InternalSystemException -> throw SystemException(result.takeException() ?: "")
            is Result.Error.BusinessException -> throw BusinessException(result.takeException() ?: "")
        }.exhaustive
    }

    post("authenticate") {
        val loginRequest = runCatching { call.receive<LoginRequest>() }.getOrElse {
            throw BadRequestException("Api require username and password to process")
        }

        when (val result =
            loginUseCase(LoginParam(username = loginRequest.username, password = loginRequest.password))) {
            is Result.Success -> sendOk(LoginTokenResponse(result.data))
            is Result.Error.InternalSystemException -> throw SystemException(result.takeException() ?: "")
            is Result.Error.BusinessException -> throw BusinessException(result.takeException() ?: "")
        }.exhaustive
    }
}