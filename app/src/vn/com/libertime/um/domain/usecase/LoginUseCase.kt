package vn.com.libertime.um.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.auth.TokenProvider
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.um.domain.entity.CredentialEntity
import vn.com.libertime.um.domain.entity.Credential
import vn.com.libertime.um.domain.service.UserService
import vn.com.libertime.um.domain.service.PasswordManagerContract

data class LoginParam(val username: String, val password: String)

@KoinApiExtension
class LoginUseCase : UseCase<LoginParam, CredentialEntity> {
    private val userService by inject<UserService>()
    private val passwordManager by inject<PasswordManagerContract>()
    private val tokenProvider by inject<TokenProvider>()

    override suspend operator fun invoke(params: LoginParam): Result<CredentialEntity> {
        val userEntity =
            userService.getUserByName(params.username)
                ?: return Result.Error.BusinessException("User is not found")
        if (!passwordManager.validatePassword(params.password, userEntity.password)) {
            return Result.Error.BusinessException("Credentials is invalid")
        }
        val credentials: CredentialEntity =
            tokenProvider.createTokens(Credential(userEntity.userId))
        return Result.Success(credentials)
    }
}