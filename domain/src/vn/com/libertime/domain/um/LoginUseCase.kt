package vn.com.libertime.domain.um

import vn.com.libertime.common.Result
import vn.com.libertime.common.UseCase
import vn.com.libertime.port.um.entity.CredentialEntity
import vn.com.libertime.port.um.provided.LoginParam
import vn.com.libertime.port.um.required.PasswordEncryptable
import vn.com.libertime.port.um.required.TokenProvidable
import vn.com.libertime.port.um.required.UserRepository

internal class LoginUseCase(
    private val userService: UserRepository,
    private val encryptedPassword: PasswordEncryptable,
    private val tokenProvider: TokenProvidable
) :
    UseCase<LoginParam, CredentialEntity> {

    override suspend operator fun invoke(params: LoginParam): Result<CredentialEntity> {
        val userEntity =
            userService.getUserByName(params.username)
                ?: return Result.Error.BusinessException("User is not found")
        if (!encryptedPassword.validatePassword(params.password, userEntity.password)) {
            return Result.Error.BusinessException("Credentials is invalid")
        }
        val credentials: CredentialEntity =
            tokenProvider.createTokens(userEntity.userId)
        return Result.Success(credentials)
    }
}