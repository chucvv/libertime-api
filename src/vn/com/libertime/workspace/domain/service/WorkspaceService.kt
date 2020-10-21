package vn.com.libertime.workspace.domain.service

import vn.com.libertime.workspace.domain.entity.WorkspaceEntity

interface WorkspaceService {
    suspend fun create(params: CreateWorkspaceParam): WorkspaceEntity?
    suspend fun list(userId: Long): List<WorkspaceEntity>
    suspend fun delete(id: Int): WorkspaceEntity?
    suspend fun workspaceByName(name: String): WorkspaceEntity?
}

data class CreateWorkspaceParam(val userId: Long, val name: String)