package vn.com.libertime.adapter.configuration

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.util.*
import vn.com.libertime.adapter.client_side.um.model.toCredential
import vn.com.libertime.adapter.server_side.service.JwtConfigService
import vn.com.libertime.adapter.server_side.service.claim
import vn.com.libertime.common.takeSuccess
import vn.com.libertime.port.um.provided.UserService
import vn.com.libertime.port.um.required.TokenProvidable

@KtorExperimentalAPI
public class JwtAppConfiguration(
    private val userService: UserService,
    private val tokenProvidable: TokenProvidable
) : AppConfigurable {
    override fun apply(application: Application) {
        application.install(Authentication) {
            jwt("jwt") {
                verifier((tokenProvidable as JwtConfigService).verifier)
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