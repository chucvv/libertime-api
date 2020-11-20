package vn.com.libertime.port.provided.user_auth

import vn.com.libertime.common.Result
import vn.com.libertime.port.provided.entity.CredentialEntity

data class LoginParam(val username: String, val password: String)

data class RegisterParam(val userName: String, val password: String)

interface AuthService {
    suspend fun login(params: LoginParam): Result<CredentialEntity>

    suspend fun register(params: RegisterParam): Result<CredentialEntity>
}