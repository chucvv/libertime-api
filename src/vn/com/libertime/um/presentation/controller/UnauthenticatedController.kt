package vn.com.libertime.um.presentation.controller

import io.ktor.application.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.core.component.KoinApiExtension
import org.koin.ktor.ext.inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import vn.com.libertime.extension.exhaustive
import vn.com.libertime.extension.sendOk
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.takeException
import vn.com.libertime.statuspages.MissingArgumentException
import vn.com.libertime.statuspages.StorageException
import vn.com.libertime.um.domain.entity.LoginParam
import vn.com.libertime.um.domain.entity.RegisterParam
import vn.com.libertime.um.domain.usecase.LoginUseCase
import vn.com.libertime.um.domain.usecase.RegisterUseCase
import vn.com.libertime.um.presentation.model.LoginTokenResponse
import vn.com.libertime.um.presentation.model.RegisterResponse

@KoinApiExtension
fun Route.registrationModule() {
    val logger: Logger = LoggerFactory.getLogger("Unauthenticated-Controller")

    val loginUseCase by inject<LoginUseCase>()
    val registerUseCase by inject<RegisterUseCase>()

    post("/user") {
        val parameters = call.receiveParameters()
        val userName = parameters["username"] ?: throw MissingArgumentException("Need user name")
        val password = parameters["password"] ?: throw MissingArgumentException("Need password")
        val email = parameters["email"]

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
            is Result.Error.StorageException -> throw StorageException(result.takeException() ?: "")
            is Result.Error.BusinessException -> call.respond(BadRequest, result.takeException() ?: "")
        }.exhaustive
    }

    post("/authenticate") {
        val parameters = call.receiveParameters()
        val userName = parameters["username"] ?: throw MissingArgumentException("Need user name")
        val password = parameters["password"] ?: throw MissingArgumentException("Need password")

        when (val result = loginUseCase(LoginParam(userName = userName, password = password))) {
            is Result.Success -> sendOk(LoginTokenResponse(result.data))
            is Result.Error.StorageException -> throw StorageException(result.takeException() ?: "")
            is Result.Error.BusinessException -> call.respond(Unauthorized, result.takeException() ?: "")
        }
        logger.info("User [$userName] login")
    }
}