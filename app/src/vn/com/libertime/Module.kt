package vn.com.libertime

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
import vn.com.libertime.adapter.client_side.um.controller.AuthController
import vn.com.libertime.adapter.client_side.um.controller.UserController
import vn.com.libertime.adapter.client_side.um.route.auth
import vn.com.libertime.adapter.client_side.um.route.user
import vn.com.libertime.adapter.configuration.AppConfigurable
import vn.com.libertime.adapter.statuspages.businessStatusPages
import vn.com.libertime.adapter.statuspages.commonStatusPages

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

    val jwtVerifier by inject<AppConfigurable>()
    install(Authentication) {
        jwtVerifier.apply(this)
    }

    val authController by inject<AuthController>()
    val userController by inject<UserController>()

    install(Routing) {
        static("/static") {
            resources("static")
        }
        auth(authController)
        authenticate("jwt") {
            user(userController)
        }
    }
}
