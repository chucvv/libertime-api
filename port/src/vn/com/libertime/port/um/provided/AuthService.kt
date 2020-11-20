package vn.com.libertime.port.um.provided

import vn.com.libertime.common.Result
import vn.com.libertime.port.um.entity.CredentialEntity
import vn.com.libertime.port.um.required.RegisterParam

data class LoginParam(val username: String, val password: String)

interface AuthService {
    suspend fun login(params: LoginParam): Result<CredentialEntity>

    suspend fun register(params: RegisterParam): Result<CredentialEntity>
}