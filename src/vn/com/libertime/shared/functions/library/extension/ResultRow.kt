package vn.com.libertime.shared.functions.library.extension

import org.jetbrains.exposed.sql.ResultRow
import vn.com.libertime.um.data.model.Users
import vn.com.libertime.um.domain.entity.UserInfoEntity
import vn.com.libertime.workspace.data.model.Workspaces
import vn.com.libertime.workspace.domain.entity.WorkspaceEntity

fun ResultRow.toUserEntity() = UserInfoEntity(
        userId = get(Users.userId),
        userName = get(Users.username),
        password = get(Users.password),
        email = get(Users.email),
        createdDate = get(Users.createdDate),
        lastLoginDate = get(Users.lastLoginDate)
)

fun ResultRow.toWorkspaceEntity() = WorkspaceEntity(
        id = get(Workspaces.id),
        userId = get(Workspaces.userId),
        name = get(Workspaces.name),
        createdDate = get(Workspaces.createdDate)
)