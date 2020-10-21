package vn.com.libertime.workspace.data.service

import vn.com.libertime.workspace.data.service.dao.WorkspaceDao
import vn.com.libertime.workspace.domain.entity.WorkspaceEntity
import vn.com.libertime.workspace.domain.service.CreateWorkspaceParam
import vn.com.libertime.workspace.domain.service.WorkspaceService

class DefaultWorkspaceService(private val workspaceDao: WorkspaceDao) : WorkspaceService {
    override suspend fun create(params: CreateWorkspaceParam): WorkspaceEntity? = workspaceDao.create(params)
    override suspend fun list(userId: Long): List<WorkspaceEntity> = workspaceDao.list(userId)
    override suspend fun delete(id: Int): WorkspaceEntity? = workspaceDao.delete(id)
    override suspend fun workspaceByName(name: String): WorkspaceEntity? = workspaceDao.workspaceByName(name)
}