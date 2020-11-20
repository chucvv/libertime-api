package vn.com.libertime.adapter.di

import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import vn.com.libertime.adapter.user_management.controller.AuthController
import vn.com.libertime.adapter.user_management.controller.DefaultAuthController
import vn.com.libertime.adapter.user_management.controller.DefaultUserController
import vn.com.libertime.adapter.user_management.controller.UserController

@KoinApiExtension
object ControllerProvider {
    private val userControllerModule = module {
        single { DefaultAuthController(get()) as AuthController }
        single { DefaultUserController(get()) as UserController }
    }

    val dependencies = listOf(userControllerModule)
}