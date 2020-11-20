package vn.com.libertime.adapter.configuration

import com.auth0.jwt.interfaces.JWTVerifier
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.routing.*
import org.koin.core.component.KoinApiExtension
import vn.com.libertime.adapter.um.model.Credential
import vn.com.libertime.adapter.um.model.toCredential
import vn.com.libertime.adapter.um.service.claim
import vn.com.libertime.common.takeSuccess
import vn.com.libertime.port.um.provided.UserService

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
                userService.getUser(userId).takeSuccess()?.user?.toCredential()
            }
        }
    }
}