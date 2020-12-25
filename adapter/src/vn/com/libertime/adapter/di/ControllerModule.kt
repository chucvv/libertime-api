package vn.com.libertime.adapter.di

import org.koin.core.module.Module
import org.koin.dsl.module
import vn.com.libertime.adapter.client_side.um.controller.AuthController
import vn.com.libertime.adapter.client_side.um.controller.UserController
import vn.com.libertime.adapter.client_side.um.controller.UserLocationController
import vn.com.libertime.chatting.controller.ChattingController
import vn.com.libertime.chatting.controller.ChattingControllerComposite
import vn.com.libertime.chatting.controller.DefaultChattingController

public object ControllerModule {
    private val userControllerModule = module {
        single { AuthController(get()) }
        single { UserController(get()) }
        single { UserLocationController(get()) }
    }

    private val chattingModule = module {
        single {
            val defaultChattingController = DefaultChattingController()
            ChattingControllerComposite().register(defaultChattingController) as ChattingController
        }
    }

    public val dependencies: List<Module> = listOf(userControllerModule, chattingModule)
}