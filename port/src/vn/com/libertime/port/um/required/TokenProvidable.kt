package vn.com.libertime.port.um.required

import vn.com.libertime.port.um.entity.CredentialEntity

public interface TokenProvidable {
    public fun createTokens(userId: String): CredentialEntity
    public fun verifyToken(token: String): Int?
}