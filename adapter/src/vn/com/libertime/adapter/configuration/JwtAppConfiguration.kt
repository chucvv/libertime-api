package vn.com.libertime.adapter.configuration

import com.auth0.jwt.interfaces.JWTVerifier
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import vn.com.libertime.adapter.client_side.um.model.toCredential
import vn.com.libertime.adapter.server_side.service.claim
import vn.com.libertime.common.takeSuccess
import vn.com.libertime.port.um.provided.UserService

class JwtAppConfiguration(
    private val userService: UserService,
    private val tokenVerifier: JWTVerifier
) : AppConfigurable {
    override fun apply(application: Application) {
        application.install(Authentication) {
            jwt("jwt") {
                verifier(tokenVerifier)
                realm = "ktor.io"
                validate {
                    it.payload.getClaim(claim)?.asString()?.let { userId ->
                        userService.getUser(userId).takeSuccess()?.user?.toCredential()
                    }
                }
            }
        }
    }
}