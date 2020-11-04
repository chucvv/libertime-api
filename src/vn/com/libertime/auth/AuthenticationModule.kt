package vn.com.libertime.auth

import com.auth0.jwt.interfaces.JWTVerifier
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.routing.*
import org.koin.core.component.KoinApiExtension
import vn.com.libertime.shared.functions.library.takeSuccess
import vn.com.libertime.um.domain.entity.UserCredentialsEntity
import vn.com.libertime.um.domain.usecase.GetUserByIdUseCase

@KoinApiExtension
fun Authentication.Configuration.authenticationModule(
    getUserByIdUseCase: GetUserByIdUseCase,
    tokenVerifier: JWTVerifier
) {
    /**
     * Setup the JWT authentication to be used in [Routing].
     * If the token is valid, the corresponding [UserCredentialsEntity] is fetched from the database.
     * The [UserCredentialsEntity] can then be accessed in each [ApplicationCall].
     */
    jwt("jwt") {
        verifier(tokenVerifier)
        realm = "ktor.io"
        validate {
            it.payload.getClaim("id").asLong()?.let { userId ->
                getUserByIdUseCase(userId).takeSuccess()?.userEntity?.toUserCredentialsEntity()
            }
        }
    }
}