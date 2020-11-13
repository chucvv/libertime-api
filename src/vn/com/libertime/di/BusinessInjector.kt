package vn.com.libertime.di

import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import vn.com.libertime.um.domain.usecase.*

@KoinApiExtension
object BusinessInjector {
    val userUseCases = module {
        single { LoginUseCase() }
        single { RegisterUseCase() }
        single { GetUserByIdUseCase() }
        single { UpdateUserInfoUseCase() }
    }
}