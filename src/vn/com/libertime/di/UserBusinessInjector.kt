package vn.com.libertime.di

import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import vn.com.libertime.um.domain.usecase.LoginUseCase
import vn.com.libertime.um.domain.usecase.RegisterUseCase

@KoinApiExtension
object UserBusinessInjector {
    val useCases = module {
        single { LoginUseCase() }
        single { RegisterUseCase() }
    }
}