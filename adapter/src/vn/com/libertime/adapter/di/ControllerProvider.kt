package vn.com.libertime.adapter.di

import org.koin.core.component.KoinApiExtension
import org.koin.core.module.Module
import org.koin.dsl.module
import vn.com.libertime.adapter.client_side.chatting.controller.ChattingController
import vn.com.libertime.adapter.client_side.chatting.controller.ChattingControllerComposite
import vn.com.libertime.adapter.client_side.chatting.controller.DefaultChattingController
import vn.com.libertime.adapter.client_side.um.controller.AuthController
import vn.com.libertime.adapter.client_side.um.controller.DefaultAuthController
import vn.com.libertime.adapter.client_side.um.controller.DefaultUserController
import vn.com.libertime.adapter.client_side.um.controller.UserController

@KoinApiExtension
public object ControllerProvider {
    private val userControllerModule = module {
        single { DefaultAuthController(get()) as AuthController }
        single { DefaultUserController(get()) as UserController }
    }

    private val chattingModule = module {
        single {
            val defaultChattingController = DefaultChattingController()
            ChattingControllerComposite().register(defaultChattingController) as ChattingController
        }
    }

    public val dependencies: List<Module> = listOf(userControllerModule, chattingModule)
}