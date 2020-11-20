package vn.com.libertime.adapter.configuration

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.locations.*
import io.ktor.request.*
import org.slf4j.event.Level
import vn.com.libertime.adapter.statuspages.businessStatusPages
import vn.com.libertime.adapter.statuspages.commonStatusPages

class CommonAppConfiguration(private val environment: String) : AppConfigurable {
    override fun apply(application: Application) {
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
            header("X-Environment", environment)
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