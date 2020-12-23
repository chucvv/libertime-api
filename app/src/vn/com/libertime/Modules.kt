package vn.com.libertime

import io.ktor.util.*
import org.koin.core.component.KoinApiExtension
import org.koin.core.module.Module
import vn.com.libertime.adapter.di.*
import vn.com.libertime.common.extension.concatenate
import vn.com.libertime.domain.di.UserDomainProvider

@KtorExperimentalAPI
@OptIn(KoinApiExtension::class)
internal val injectedModules: List<Module> = concatenate(
    ConfigurationModule.dependencies,
    AuthTokenModule.dependencies,
    UserDomainProvider.dependencies,
    ControllerModule.dependencies,
    DaoModule.dependencies,
    KafkaModule.dependencies
)