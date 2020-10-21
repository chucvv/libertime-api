package vn.com.libertime.um.presentation.controller

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.core.component.KoinApiExtension
import org.koin.ktor.ext.inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.extension.exhaustive
import vn.com.libertime.shared.functions.library.extension.sendOk
import vn.com.libertime.shared.functions.library.takeException
import vn.com.libertime.statuspages.BusinessException
import vn.com.libertime.statuspages.MissingArgumentException
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
    val logger: Logger = LoggerFactory.getLogger("Unauthenticated-Controller")

    val loginUseCase by inject<LoginUseCase>()
    val registerUseCase by inject<RegisterUseCase>()

    post("user") {
        val request = call.receive<RegisterRequest>()
        val userName = request.username ?: throw MissingArgumentException("Need user name")
        val password = request.password ?: throw MissingArgumentException("Need password")
        val email = request.email

        when (val result = registerUseCase(
            RegisterParam(
                userName = userName,
                password = password,
                email = email
            )
        )) {
            is Result.Success -> {
                val data = result.data
                val registerResponse =
                    RegisterResponse(id = data.userId, userName = data.userName, createdDate = data.createdDate)
                sendOk(registerResponse)
            }
            is Result.Error.InternalSystemException -> throw SystemException(result.takeException() ?: "")
            is Result.Error.BusinessException -> throw BusinessException(result.takeException() ?: "")
        }.exhaustive
    }

    post("authenticate") {
        val request = call.receive<LoginRequest>()
        val userName = request.username ?: throw MissingArgumentException("Need user name")
        val password = request.password ?: throw MissingArgumentException("Need password")

        when (val result = loginUseCase(LoginParam(userName = userName, password = password))) {
            is Result.Success -> sendOk(LoginTokenResponse(result.data))
            is Result.Error.InternalSystemException -> throw SystemException(result.takeException() ?: "")
            is Result.Error.BusinessException -> throw BusinessException(result.takeException() ?: "")
        }.exhaustive
        logger.info("User [$userName] login")
    }
}