package vn.com.libertime.workspace.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.workspace.domain.entity.WorkspaceEntity
import vn.com.libertime.workspace.domain.service.WorkspaceService

@KoinApiExtension
class DeleteWorkspaceUseCase : UseCase<Int, WorkspaceEntity> {
    private val workspaceUseCase by inject<WorkspaceService>()

    override suspend operator fun invoke(id: Int): Result<WorkspaceEntity> {
        try {
            val workspaceEntity: WorkspaceEntity =
                workspaceUseCase.delete(id) ?: return Result.Error.BusinessException("Deleting is uncompleted")
            return Result.Success(workspaceEntity)
        } catch (ex: Exception) {
            return Result.Error.InternalSystemException(ex.message)
        }
    }
}