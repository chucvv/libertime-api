package vn.com.libertime.um.data.extension

import org.jetbrains.exposed.sql.ResultRow
import vn.com.libertime.um.data.model.Users
import vn.com.libertime.um.domain.entity.UserInfoEntity

fun ResultRow.toUserEntity() = UserInfoEntity(
        userId = get(Users.userId),
        userName = get(Users.username),
        password = get(Users.password),
        email = get(Users.email),
        createdDate = get(Users.createdDate),
        lastLoginDate = get(Users.lastLoginDate)
)
