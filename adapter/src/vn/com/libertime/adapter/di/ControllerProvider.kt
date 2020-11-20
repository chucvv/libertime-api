package vn.com.libertime.adapter.di

import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import vn.com.libertime.adapter.um.controller.AuthController
import vn.com.libertime.adapter.um.controller.DefaultAuthController
import vn.com.libertime.adapter.um.controller.DefaultUserController
import vn.com.libertime.adapter.um.controller.UserController

@KoinApiExtension
object ControllerProvider {
    private val userControllerModule = module {
        single { DefaultAuthController(get()) as AuthController }
        single { DefaultUserController(get()) as UserController }
    }

    val dependencies = listOf(userControllerModule)
}