package vn.com.libertime.application

import com.auth0.jwt.interfaces.JWTVerifier
import com.fasterxml.jackson.databind.SerializationFeature
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.content.*
import io.ktor.jackson.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.routing.*
import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import io.lettuce.core.api.StatefulRedisConnection
import org.jetbrains.exposed.sql.Database
import org.koin.core.component.KoinApiExtension
import org.koin.ktor.ext.inject
import org.slf4j.event.Level
import vn.com.libertime.auth.authenticationModule
import vn.com.libertime.statuspages.authStatusPages
import vn.com.libertime.statuspages.generalStatusPages
import vn.com.libertime.um.domain.usecase.GetUserByIdUseCase
import vn.com.libertime.um.presentation.controller.registrationModule
import vn.com.libertime.um.presentation.controller.userModule
import vn.com.libertime.workspace.presentation.controller.workspaceModule

fun isProduction(environment: String): Boolean = environment == productionEnvironment

fun initDB(environment: String) {
    val config = HikariConfig("/${environment}_hikari.properties")
    config.schema = "public"
    config.maximumPoolSize = 3
    config.isAutoCommit = false
    config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    config.validate()
    val ds = HikariDataSource(config)
    Database.connect(ds)
}

fun initRedis(environment: String) {
    val redisURI = RedisURI.builder().withHost("localhost").withPort(6379).withDatabase(1).build()
    val redisClient = RedisClient.create(redisURI)
    val connection: StatefulRedisConnection<String, String> = redisClient.connect()
}

@KoinApiExtension
fun Application.setupModules(environment: String) {
    initDB(environment)
    initRedis(environment)
    install(Locations)
    install(DefaultHeaders) {
        header("X-Engine", "Ktor")
        header("X-Environment", environment)
    }
    install(CallLogging) {
        level = if (isProduction(environment)) {
            Level.INFO
        } else {
            Level.WARN
        }
        filter { call -> call.request.path().startsWith("/") }
    }
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
    install(StatusPages) {
        generalStatusPages()
        authStatusPages()
    }
    install(Authentication) {
        val jwtVerifier by inject<JWTVerifier>()
        val getUserByIdUseCase by inject<GetUserByIdUseCase>()
        authenticationModule(getUserByIdUseCase, jwtVerifier)
    }
    install(Routing) {
        static("/static") {
            resources("static")
        }
        registrationModule()
        authenticate("jwt") {
            userModule()
            workspaceModule()
        }
    }
}