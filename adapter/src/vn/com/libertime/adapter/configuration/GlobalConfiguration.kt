package vn.com.libertime.adapter.configuration

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.sessions.*
import io.ktor.util.*
import io.ktor.websocket.*
import org.slf4j.event.Level
import vn.com.libertime.adapter.statuspages.businessStatusPages
import vn.com.libertime.adapter.statuspages.commonStatusPages
import vn.com.libertime.chatting.model.ClientSession
import vn.com.libertime.port.um.required.EnvironmentProvidable

public class GlobalConfiguration(private val environmentProvidable: EnvironmentProvidable) : AppConfigurable {
    @KtorExperimentalAPI
    override fun apply(application: Application) {
        application.install(WebSockets)
        application.install(Sessions) {
            cookie<ClientSession>("SESSION")
        }
        application.intercept(ApplicationCallPipeline.Features) {
            if (call.sessions.get<ClientSession>() == null) {
                call.sessions.set(ClientSession(generateNonce()))
            }
        }
        application.install(CORS) {
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Put)
            method(HttpMethod.Delete)
            header(HttpHeaders.Authorization)
            allowCredentials = true
            anyHost()
        }
        application.install(Locations)
        application.install(DefaultHeaders) {
            header("X-Engine", "Ktor")
            header("X-Environment", environmentProvidable.deployEnvironment)
        }
        application.install(CallLogging) {
            level = Level.INFO
            filter { call -> call.request.path().startsWith("/") }
        }
        application.install(ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }
        application.install(StatusPages) {
            commonStatusPages()
            businessStatusPages()
        }
    }

}