package vn.com.libertime.um.presentation.controller

import io.ktor.application.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.InternalServerError
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
import vn.com.libertime.statuspages.MissingArgumentException
import vn.com.libertime.um.domain.entity.LoginEntity
import vn.com.libertime.um.domain.entity.RegisterEntity
import vn.com.libertime.um.domain.usecase.LoginUseCase
import vn.com.libertime.um.domain.usecase.RegisterUseCase

@KoinApiExtension
fun Route.userRoutes() {
    val logger: Logger = LoggerFactory.getLogger("UserController")

    val loginUseCase by inject<LoginUseCase>()
    val registerUseCase by inject<RegisterUseCase>()

    route("/user") {
        post("/register") {
            val parameters = call.receiveParameters()
            val userName = parameters["username"] ?: run {
                throw MissingArgumentException("Need user name")
            }
            val password = parameters["password"] ?: run {
                throw MissingArgumentException("Need password")
            }

            when (registerUseCase(
                RegisterEntity(
                    userName = userName,
                    password = password,
                    email = ""
                )
            )) {
                is Result.Success -> {
                    sendOk()
                    logger.info("Register user success $userName")
                }
                is Result.Error.StorageException -> call.respond(InternalServerError)
                is Result.Error.BusinessException -> call.respond(BadRequest)
            }.exhaustive
        }

        post("/login") {
            val parameters = call.receiveParameters()
            val userName = parameters["username"] ?: run {
                throw MissingArgumentException("Need user name")
            }
            val password = parameters["password"] ?: run {
                throw MissingArgumentException("Need password")
            }
            when (loginUseCase(LoginEntity(userName = userName, password = password))) {
                is Result.Success -> sendOk()
                is Result.Error.StorageException -> call.respond(InternalServerError)
                is Result.Error.BusinessException -> call.respond(Unauthorized)
            }
            logger.info("User [$userName] login")
        }
    }
}