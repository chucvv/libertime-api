package vn.com.libertime.um.presentation.controller

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.core.component.KoinApiExtension
import org.koin.ktor.ext.inject
import vn.com.libertime.application.user
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.extension.exhaustive
import vn.com.libertime.shared.functions.library.extension.sendOk
import vn.com.libertime.shared.functions.library.takeException
import vn.com.libertime.statuspages.AuthorizationException
import vn.com.libertime.statuspages.BusinessException
import vn.com.libertime.statuspages.SystemException
import vn.com.libertime.um.domain.entity.UserCredentialsEntity
import vn.com.libertime.um.domain.usecase.GetUserByIdUseCase
import vn.com.libertime.um.domain.usecase.UpdateUserInfoUseCase
import vn.com.libertime.um.domain.usecase.UpdateUserParam
import vn.com.libertime.um.presentation.`object`.MeResponse
import vn.com.libertime.um.presentation.`object`.UpdateProfileRequest

@KoinApiExtension
fun Route.userModule() {
    val updateUserInfoUseCase by inject<UpdateUserInfoUseCase>()
    val getUserByIdUseCase by inject<GetUserByIdUseCase>()

    route("me") {
        get {
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
                is Result.Error.InternalSystemException -> throw SystemException(result.takeException() ?: "")
                is Result.Error.BusinessException -> throw BusinessException(result.takeException() ?: "")
            }
        }

        put {
            val user: UserCredentialsEntity = call.user ?: throw AuthorizationException()
            val request = call.receive<UpdateProfileRequest>()
            when (val result = updateUserInfoUseCase(UpdateUserParam(user.userId, request.username, request.email))) {
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
                is Result.Error.InternalSystemException -> throw SystemException(result.takeException() ?: "")
                is Result.Error.BusinessException -> throw BusinessException(result.takeException() ?: "")
            }.exhaustive
        }
    }
}