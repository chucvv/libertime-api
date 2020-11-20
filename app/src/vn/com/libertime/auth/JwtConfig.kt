package vn.com.libertime.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import vn.com.libertime.usermanagement.domain.entity.Credential
import vn.com.libertime.usermanagement.domain.entity.CredentialEntity
import java.util.*

const val claim = "id"

class JwtConfig private constructor(secret: String) : TokenProvider {
    private val algorithm = Algorithm.HMAC256(secret)
    val verifier: JWTVerifier = JWT.require(algorithm)
        .withIssuer(issuer)
        .withAudience(audience)
        .build()

    override fun verifyToken(token: String): Int? {
        return verifier.verify(token).claims[claim]?.asInt()
    }

    /**
     * Produce token and refresh token for this combination of User and Account
     */
    override fun createTokens(credential: Credential) = CredentialEntity(
        createToken(credential, getTokenExpiration()),
        createToken(credential, getTokenExpiration(refreshValidityInMs))
    )

    private fun createToken(credential: Credential, expiration: Date) = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withAudience(audience)
        .withClaim(claim, credential.userId)
        .withExpiresAt(expiration)
        .sign(algorithm)

    /**
     * Calculate the expiration Date based on current time + the given validity
     */
    private fun getTokenExpiration(validity: Long = validityInMs) = Date(System.currentTimeMillis() + validity)

    companion object {
        lateinit var instance: JwtConfig

        fun initialize(secret: String) {
            synchronized(this) {
                if (!this::instance.isInitialized) {
                    instance = JwtConfig(secret)
                }
            }
        }

        private const val issuer = "libertime.com.vn.issuer"
        private const val audience = "libertime.com.vn"
        private const val validityInMs: Long = 3600000L * 24L
        private const val refreshValidityInMs: Long = 3600000L * 24L * 30L
    }
}

interface TokenProvider {
    fun createTokens(credential: Credential): CredentialEntity
    fun verifyToken(token: String): Int?
}