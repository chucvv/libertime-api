package vn.com.libertime.di

import org.koin.core.component.KoinApiExtension
import vn.com.libertime.adapter.di.AuthTokenProvider
import vn.com.libertime.adapter.di.ControllerProvider
import vn.com.libertime.adapter.di.RepositoryProvider
import vn.com.libertime.domain.di.UserDomainProvider

@OptIn(KoinApiExtension::class)
val injectedModules = concatenate(
    AuthTokenProvider.dependencies,
    UserDomainProvider.dependencies,
    ControllerProvider.dependencies,
    RepositoryProvider.dependencies,
)

fun <T> concatenate(vararg lists: List<T>): List<T> {
    return listOf(*lists).flatten()
}