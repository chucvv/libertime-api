package vn.com.libertime.workspace.presentation.controller

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
import vn.com.libertime.statuspages.MissingArgumentException
import vn.com.libertime.statuspages.SystemException
import vn.com.libertime.workspace.domain.service.CreateWorkspaceParam
import vn.com.libertime.workspace.domain.usecase.CreateWorkspaceUseCase
import vn.com.libertime.workspace.domain.usecase.DeleteWorkspaceUseCase
import vn.com.libertime.workspace.domain.usecase.ListingUseCase
import vn.com.libertime.workspace.presentation.`object`.CreateWorkspaceRequest
import vn.com.libertime.workspace.presentation.`object`.DeleteWorkspaceRequest

@KoinApiExtension
fun Route.workspaceModule() {
    val createWorkspaceUseCase by inject<CreateWorkspaceUseCase>()
    val listingUseCase by inject<ListingUseCase>()
    val deleteWorkspaceUseCase by inject<DeleteWorkspaceUseCase>()

    route("workspace") {
        post {
            val user = call.user ?: throw AuthorizationException()
            val request = call.receive<CreateWorkspaceRequest>()
            val wsName = request.name ?: throw MissingArgumentException("Need a name for ws")
            when (val result = createWorkspaceUseCase(CreateWorkspaceParam(userId = user.userId, name = wsName))) {
                is Result.Success -> sendOk(data = result.data)
                is Result.Error.InternalSystemException -> throw SystemException(result.takeException() ?: "")
                is Result.Error.BusinessException -> throw BusinessException(result.takeException() ?: "")
            }.exhaustive
        }

        get {
            val user = call.user ?: throw AuthorizationException()
            when (val result = listingUseCase(userId = user.userId)) {
                is Result.Success -> sendOk(data = result.data)
                is Result.Error.InternalSystemException -> throw SystemException(result.takeException() ?: "")
                is Result.Error.BusinessException -> throw BusinessException(result.takeException() ?: "")
            }.exhaustive
        }

        delete {
            call.user ?: throw AuthorizationException()
            val request = call.receive<DeleteWorkspaceRequest>()
            val wsId = request.id ?: throw MissingArgumentException("Need a id for deleting ws")
            when (val result = deleteWorkspaceUseCase(wsId)) {
                is Result.Success -> sendOk(data = result.data)
                is Result.Error.InternalSystemException -> throw SystemException(result.takeException() ?: "")
                is Result.Error.BusinessException -> throw BusinessException(result.takeException() ?: "")
            }.exhaustive
        }
    }
}