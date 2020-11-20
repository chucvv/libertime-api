package vn.com.libertime.adapter.configuration

import com.auth0.jwt.interfaces.JWTVerifier
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.routing.*
import org.koin.core.component.KoinApiExtension
import vn.com.libertime.adapter.user_management.model.Credential
import vn.com.libertime.adapter.user_management.model.toCredential
import vn.com.libertime.adapter.user_management.service.claim
import vn.com.libertime.common.takeSuccess
import vn.com.libertime.port.provided.user_auth.UserService

@KoinApiExtension
fun Authentication.Configuration.authenticationModule(
    userService: UserService,
    tokenVerifier: JWTVerifier
) {
    /**
     * Setup the JWT authentication to be used in [Routing].
     * If the token is valid, the corresponding [Credential] is fetched from the database.
     * The [Credential] can then be accessed in each [ApplicationCall].
     */
    jwt("jwt") {
        verifier(tokenVerifier)
        realm = "ktor.io"
        validate {
            it.payload.getClaim(claim)?.asString()?.let { userId ->
                userService.getUserByUserId(userId).takeSuccess()?.user?.toCredential()
            }
        }
    }
}