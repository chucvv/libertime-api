package vn.com.libertime.domain.um

import vn.com.libertime.common.Result
import vn.com.libertime.port.um.entity.CredentialEntity
import vn.com.libertime.port.um.provided.AuthService
import vn.com.libertime.port.um.provided.LoginParam
import vn.com.libertime.port.um.required.RegisterParam

class DefaultAuthService(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : AuthService {
    override suspend fun login(params: LoginParam): Result<CredentialEntity> = loginUseCase(params)
    override suspend fun register(params: RegisterParam): Result<CredentialEntity> = registerUseCase(params)
}