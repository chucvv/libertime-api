package vn.com.libertime.um.presentation.controller

import io.ktor.application.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.InternalServerError
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.core.component.KoinApiExtension
import org.koin.ktor.ext.inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import vn.com.libertime.extension.exhaustive
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.um.domain.param.LoginParam
import vn.com.libertime.um.domain.param.RegisterParam
import vn.com.libertime.um.domain.usecase.LoginUseCase
import vn.com.libertime.um.domain.usecase.RegisterUseCase

@KoinApiExtension
fun Route.userRoutes() {
    val logger: Logger = LoggerFactory.getLogger("UserController")

    val loginUseCase: LoginUseCase by inject()
    val registerUseCase: RegisterUseCase by inject()

    route("/user") {
        post("/register") {
            val parameters = call.receiveParameters()
            val userName = parameters["username"] ?: run {
                call.respond(BadRequest, "Need user name")
                return@post
            }
            val password = parameters["password"] ?: run {
                call.respond(BadRequest, "Need password")
                return@post
            }

            when (registerUseCase(RegisterParam(userName = userName, password = password, email = ""))) {
                is Result.Success -> {
                    call.respond(OK, "Welcome $userName")
                    logger.info("Register user success [$userName]")
                }
                is Result.Error.StorageException -> call.respond(InternalServerError)
                is Result.Error.BusinessException -> call.respond(BadRequest)
            }.exhaustive
        }

        post("/login") {
            val parameters = call.receiveParameters()
            val userName = parameters["username"] ?: run {
                call.respond(BadRequest, "Need user name")
                return@post
            }
            val password = parameters["password"] ?: run {
                call.respond(BadRequest, "Need password")
                return@post
            }
            when (loginUseCase(LoginParam(userName = userName, password = password))) {
                is Result.Success -> call.respond(OK)
                is Result.Error.StorageException -> call.respond(InternalServerError)
                is Result.Error.BusinessException -> call.respond(Unauthorized)
            }
            logger.info("User [$userName] login")
        }
    }
}