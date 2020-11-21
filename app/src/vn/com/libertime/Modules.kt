package vn.com.libertime

import io.ktor.util.*
import org.koin.core.component.KoinApiExtension
import org.koin.core.module.Module
import vn.com.libertime.adapter.di.AuthTokenProvider
import vn.com.libertime.adapter.di.ConfigurationProvider
import vn.com.libertime.adapter.di.ControllerProvider
import vn.com.libertime.adapter.di.RepositoryProvider
import vn.com.libertime.common.extension.concatenate
import vn.com.libertime.database.DaoProvider
import vn.com.libertime.domain.di.UserDomainProvider

@KtorExperimentalAPI
@OptIn(KoinApiExtension::class)
internal val injectedModules: List<Module> = concatenate(
    ConfigurationProvider.dependencies,
    AuthTokenProvider.dependencies,
    UserDomainProvider.dependencies,
    ControllerProvider.dependencies,
    RepositoryProvider.dependencies,
    DaoProvider.dependencies,
)