package vn.com.libertime.adapter.configuration

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.content.*
import io.ktor.routing.*
import vn.com.libertime.adapter.client_side.um.controller.AuthController
import vn.com.libertime.adapter.client_side.um.controller.UserController
import vn.com.libertime.adapter.client_side.um.route.auth
import vn.com.libertime.adapter.client_side.um.route.user

public class BusinessAppConfiguration(
    private val authController: AuthController,
    private val userController: UserController
) : AppConfigurable {
    override fun apply(application: Application) {
        application.install(Routing) {
            static("/static") {
                resources("static")
            }
            auth(authController)
            authenticate("jwt") {
                user(userController)
            }
        }
    }

}