package vn.com.libertime.di

import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import vn.com.libertime.um.data.service.DefaultUserService
import vn.com.libertime.um.data.service.dao.DefaultUserDao
import vn.com.libertime.um.data.service.dao.UserDao
import vn.com.libertime.um.domain.service.UserService
import vn.com.libertime.workspace.data.service.DefaultWorkspaceService
import vn.com.libertime.workspace.data.service.dao.DefaultWorkspaceDao
import vn.com.libertime.workspace.data.service.dao.WorkspaceDao
import vn.com.libertime.workspace.domain.service.WorkspaceService

@KoinApiExtension
object ServiceInjector {
    val userService = module {
        single { DefaultUserDao() as UserDao }
        single { DefaultUserService(get()) as UserService }
    }

    val workspaceService = module {
        single { DefaultWorkspaceDao() as WorkspaceDao }
        single { DefaultWorkspaceService(get()) as WorkspaceService }
    }
}