package vn.com.libertime.port.um.required

import vn.com.libertime.port.um.entity.CredentialEntity

interface TokenProvidable {
    fun createTokens(userId: String): CredentialEntity
    fun verifyToken(token: String): Int?
}