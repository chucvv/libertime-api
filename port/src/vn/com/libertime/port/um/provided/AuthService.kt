package vn.com.libertime.port.um.provided

import vn.com.libertime.common.Result
import vn.com.libertime.port.um.entity.CredentialEntity

public data class LoginParam(val username: String, val password: String)

public data class RegisterParam(val userName: String, val password: String)

public interface AuthService {
    public suspend fun login(params: LoginParam): Result<CredentialEntity>

    public suspend fun register(params: RegisterParam): Result<CredentialEntity>
}