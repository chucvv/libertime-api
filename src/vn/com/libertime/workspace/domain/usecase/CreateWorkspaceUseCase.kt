package vn.com.libertime.workspace.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.workspace.domain.entity.WorkspaceEntity
import vn.com.libertime.workspace.domain.service.CreateWorkspaceParam
import vn.com.libertime.workspace.domain.service.WorkspaceService

@KoinApiExtension
class CreateWorkspaceUseCase : UseCase<CreateWorkspaceParam, WorkspaceEntity> {
    private val workspaceService by inject<WorkspaceService>()

    override suspend operator fun invoke(params: CreateWorkspaceParam): Result<WorkspaceEntity> {
        try {
            workspaceService.workspaceByName(params.name)?.run {
                return Result.Error.BusinessException("Workspace has already taken")
            }
            val workspaceEntity: WorkspaceEntity =
                workspaceService.create(params)
                    ?: return Result.Error.BusinessException("Workspace created unsuccessfully")
            return Result.Success(workspaceEntity)
        } catch (ex: Exception) {
            return Result.Error.InternalSystemException(ex.message)
        }
    }
}