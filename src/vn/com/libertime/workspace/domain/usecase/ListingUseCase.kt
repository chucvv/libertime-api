package vn.com.libertime.workspace.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.workspace.domain.entity.WorkspaceEntity
import vn.com.libertime.workspace.domain.service.WorkspaceService

@KoinApiExtension
class ListingUseCase : UseCase<Long, List<WorkspaceEntity>> {
    private val workspaceService by inject<WorkspaceService>()

    override suspend operator fun invoke(userId: Long): Result<List<WorkspaceEntity>> =
        Result.Success(workspaceService.list(userId = userId))
}