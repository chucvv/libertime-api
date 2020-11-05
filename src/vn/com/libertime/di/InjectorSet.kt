package vn.com.libertime.di

import org.koin.core.component.KoinApiExtension

@OptIn(KoinApiExtension::class)
val injectorSet = listOf(
    AuthInjector.authInjector,
    ServiceInjector.userService,
    ServiceInjector.workspaceService,
    BusinessInjector.userUseCases,
    BusinessInjector.workspaceUseCases
)