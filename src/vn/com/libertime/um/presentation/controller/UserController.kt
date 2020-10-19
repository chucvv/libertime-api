package vn.com.libertime.um.presentation.controller

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.core.component.KoinApiExtension
import org.koin.ktor.ext.inject
import vn.com.libertime.application.user
import vn.com.libertime.extension.sendOk
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.takeException
import vn.com.libertime.statuspages.AuthorizationException
import vn.com.libertime.statuspages.BusinessException
import vn.com.libertime.statuspages.StorageException
import vn.com.libertime.um.domain.entity.UpdateUserParam
import vn.com.libertime.um.domain.entity.UserCredentialsEntity
import vn.com.libertime.um.domain.usecase.GetUserByIdUseCase
import vn.com.libertime.um.domain.usecase.UpdateUserInfoUseCase
import vn.com.libertime.um.presentation.model.MeResponse

@KoinApiExtension
fun Route.userModule() {
    val updateUserInfoUseCase by inject<UpdateUserInfoUseCase>()
    val getUserByIdUseCase by inject<GetUserByIdUseCase>()
    get("me") {
        val user: UserCredentialsEntity = call.user ?: throw AuthorizationException()
        when (val result = getUserByIdUseCase(user.userId)) {
            is Result.Success -> {
                val userInfo = result.data
                sendOk(
                    MeResponse(
                        userId = user.userId,
                        userName = userInfo.userName,
                        email = userInfo.email,
                        createdDate = userInfo.createdDate
                    )
                )
            }
            is Result.Error.StorageException -> throw StorageException(result.takeException() ?: "")
            is Result.Error.BusinessException -> throw BusinessException(result.takeException() ?: "")
        }
    }
    put("updateProfile") {
        val user: UserCredentialsEntity = call.user ?: throw AuthorizationException()
        val parameters = call.receiveParameters()
        val userName = parameters["username"]
        val email = parameters["email"]
        when (val result = updateUserInfoUseCase(UpdateUserParam(user.userId, userName, email))) {
            is Result.Success -> {
                val userInfo = result.data
                sendOk(
                    MeResponse(
                        userId = user.userId,
                        userName = userInfo.userName,
                        email = userInfo.email,
                        createdDate = userInfo.createdDate
                    )
                )
            }
            is Result.Error.StorageException -> throw StorageException(result.takeException() ?: "")
            is Result.Error.BusinessException -> throw BusinessException(result.takeException() ?: "")
        }
    }
}