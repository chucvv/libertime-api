package vn.com.libertime.di

import org.koin.core.component.KoinApiExtension

@OptIn(KoinApiExtension::class)
val injectorSet = listOf(
    AuthInjector.authInjector,
    ServiceInjector.userService,
    BusinessInjector.userUseCases,
    BusinessInjector.controllers,
)