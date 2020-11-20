package vn.com.libertime.port.required

import vn.com.libertime.port.provided.entity.CredentialEntity

interface TokenProvidable {
    fun createTokens(userId: String): CredentialEntity
    fun verifyToken(token: String): Int?
}