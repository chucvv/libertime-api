package vn.com.libertime.application

import com.auth0.jwt.interfaces.JWTVerifier
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.jackson.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.core.component.KoinApiExtension
import org.koin.ktor.ext.inject
import org.slf4j.event.Level
import vn.com.libertime.auth.authenticationModule
import vn.com.libertime.statuspages.businessStatusPages
import vn.com.libertime.statuspages.commonStatusPages
import vn.com.libertime.um.domain.usecase.GetUserByIdUseCase
import vn.com.libertime.um.presentation.controller.registrationModule
import vn.com.libertime.um.presentation.controller.userModule
import vn.com.libertime.util.isProduction

fun Application.setupCommonModules(environment: String) {
    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost()
    }
    install(Locations)
    install(DefaultHeaders) {
        header("X-Engine", "Ktor")
        header("X-Environment", environment)
    }
    install(CallLogging) {
        level = if (isProduction(environment)) Level.INFO else Level.WARN
        filter { call -> call.request.path().startsWith("/") }
    }
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
}

@KoinApiExtension
fun Application.setupBusinessModules() {
    install(StatusPages) {
        commonStatusPages()
        businessStatusPages()
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
        }
    }
}
