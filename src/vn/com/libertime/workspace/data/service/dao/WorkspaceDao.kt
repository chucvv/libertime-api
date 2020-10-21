package vn.com.libertime.workspace.data.service.dao

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import vn.com.libertime.shared.functions.library.exception.DatabaseException
import vn.com.libertime.shared.functions.library.extension.toWorkspaceEntity
import vn.com.libertime.workspace.data.model.Workspaces
import vn.com.libertime.workspace.domain.entity.WorkspaceEntity
import vn.com.libertime.workspace.domain.service.CreateWorkspaceParam

interface WorkspaceDao {
    suspend fun create(params: CreateWorkspaceParam): WorkspaceEntity?
    suspend fun list(userId: Long): List<WorkspaceEntity>
    suspend fun delete(id: Int): WorkspaceEntity?
    suspend fun workspaceByName(name: String): WorkspaceEntity?
}

class DefaultWorkspaceDao : WorkspaceDao {
    init {
        transaction {
            SchemaUtils.create(Workspaces)
        }
    }

    override suspend fun create(params: CreateWorkspaceParam): WorkspaceEntity? = try {
        transaction {
            Workspaces.insert {
                it[name] = params.name
                it[userId] = params.userId
            }
            Workspaces.select { Workspaces.name eq params.name }.singleOrNull()?.toWorkspaceEntity()
        }
    } catch (ex: Exception) {
        throw DatabaseException(ex)
    }

    override suspend fun list(userId: Long): List<WorkspaceEntity> = transaction {
        Workspaces.select { Workspaces.userId eq userId }.toList().map {
            it.toWorkspaceEntity()
        }
    }

    override suspend fun delete(id: Int): WorkspaceEntity? = try {
        transaction {
            val workspaceEntity = Workspaces.select { Workspaces.id eq id }.singleOrNull()?.toWorkspaceEntity()
            Workspaces.deleteWhere { Workspaces.id eq id }
            workspaceEntity
        }
    } catch (ex: Exception) {
        throw DatabaseException(ex)
    }

    override suspend fun workspaceByName(name: String): WorkspaceEntity? = transaction {
        Workspaces.select { Workspaces.name eq name }.singleOrNull()?.toWorkspaceEntity()
    }
}

