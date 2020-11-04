package vn.com.libertime.di

import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import vn.com.libertime.um.domain.usecase.*
import vn.com.libertime.workspace.domain.usecase.CreateWorkspaceUseCase
import vn.com.libertime.workspace.domain.usecase.DeleteWorkspaceUseCase
import vn.com.libertime.workspace.domain.usecase.ListingUseCase

@KoinApiExtension
object BusinessInjector {
    val userUseCases = module {
        single { LoginUseCase() }
        single { RegisterUseCase() }
        single { GetUserByIdUseCase() }
        single { UpdateUserInfoUseCase() }
    }

    val workspaceUseCases = module {
        single { CreateWorkspaceUseCase() }
        single { ListingUseCase() }
        single { DeleteWorkspaceUseCase() }
    }
}