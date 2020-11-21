package vn.com.libertime.adapter.server_side.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.util.*
import vn.com.libertime.adapter.configuration.Config
import vn.com.libertime.port.um.entity.CredentialEntity
import vn.com.libertime.port.um.required.TokenProvidable
import java.util.*

internal const val claim = "id"

@KtorExperimentalAPI
public class JwtConfigService(config: Config) : TokenProvidable {
    private val algorithm = Algorithm.HMAC256(config.HASH_SECRET_KEY)

    public val verifier: JWTVerifier = JWT.require(algorithm)
        .withIssuer(issuer)
        .withAudience(audience)
        .build()

    override fun verifyToken(token: String): Int? {
        return verifier.verify(token).claims[claim]?.asInt()
    }

    /**
     * Produce token and refresh token for this combination of User and Account
     */
    override fun createTokens(userId: String): CredentialEntity = CredentialEntity(
        createToken(userId, getTokenExpiration()),
        createToken(userId, getTokenExpiration(refreshValidityInMs))
    )

    private fun createToken(userId: String, expiration: Date) = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withAudience(audience)
        .withClaim(claim, userId)
        .withExpiresAt(expiration)
        .sign(algorithm)

    /**
     * Calculate the expiration Date based on current time + the given validity
     */
    private fun getTokenExpiration(validity: Long = validityInMs) = Date(System.currentTimeMillis() + validity)

    public companion object {
        private const val issuer = "libertime.com.vn.issuer"
        private const val audience = "libertime.com.vn"
        private const val validityInMs: Long = 3600000L * 24L
        private const val refreshValidityInMs: Long = 3600000L * 24L * 30L
    }
}