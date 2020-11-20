package vn.com.libertime.domain.um

import vn.com.libertime.common.Result
import vn.com.libertime.common.UseCase
import vn.com.libertime.port.provided.entity.CredentialEntity
import vn.com.libertime.port.provided.user_auth.RegisterParam
import vn.com.libertime.port.required.EncryptedPassword
import vn.com.libertime.port.required.TokenProvidable
import vn.com.libertime.port.required.UserRepository

class RegisterUseCase(
    private val userService: UserRepository,
    private val encryptedPassword: EncryptedPassword,
    private val tokenProvider: TokenProvidable
) : UseCase<RegisterParam, CredentialEntity> {

    override suspend operator fun invoke(params: RegisterParam): Result<CredentialEntity> = runCatching {
        val username = params.userName
        userService.getUserByName(username)?.run {
            return@runCatching Result.Error.BusinessException("User $username has already taken")
        }
        val password = encryptedPassword.encryptPassword(params.password)
        val userId = userService.createUser(
            params.copy(password = password)
        )

        val credentials: CredentialEntity =
            tokenProvider.createTokens(userId)
        Result.Success(credentials)
    }.getOrElse {
        Result.Error.InternalSystemException(it.message)
    }
}