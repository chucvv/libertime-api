package vn.com.libertime.adapter.configuration

import com.auth0.jwt.interfaces.JWTVerifier
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import vn.com.libertime.adapter.user_management.model.toCredential
import vn.com.libertime.adapter.user_management.service.claim
import vn.com.libertime.common.takeSuccess
import vn.com.libertime.port.provided.user_auth.UserService

class JwtAppConfiguration(
    private val userService: UserService,
    private val tokenVerifier: JWTVerifier
) : AppConfigurable {
    override fun apply(configurable: Authentication.Configuration) {
        configurable.jwt("jwt") {
            verifier(tokenVerifier)
            realm = "ktor.io"
            validate {
                it.payload.getClaim(claim)?.asString()?.let { userId ->
                    userService.getUserByUserId(userId).takeSuccess()?.user?.toCredential()
                }
            }
        }
    }
}