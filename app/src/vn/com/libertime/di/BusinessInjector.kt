package vn.com.libertime.di

import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import vn.com.libertime.usermanagement.domain.usecase.*
import vn.com.libertime.usermanagement.presentation.controller.AuthController
import vn.com.libertime.usermanagement.presentation.controller.DefaultUserController
import vn.com.libertime.usermanagement.presentation.controller.DefaultAuthController
import vn.com.libertime.usermanagement.presentation.controller.UserController

@KoinApiExtension
object BusinessInjector {
    val userUseCases = module {
        single { LoginUseCase() }
        single { RegisterUseCase() }
        single { GetUserByIdUseCase() }
        single { UpdateUserInfoUseCase() }
    }

    val controllers = module {
        single { DefaultAuthController() as AuthController }
        single { DefaultUserController() as UserController }
    }
}