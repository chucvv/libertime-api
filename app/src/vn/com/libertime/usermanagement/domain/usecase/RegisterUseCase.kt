package vn.com.libertime.usermanagement.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.auth.TokenProvider
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.usermanagement.domain.entity.Credential
import vn.com.libertime.usermanagement.domain.entity.CredentialEntity
import vn.com.libertime.usermanagement.domain.service.PasswordManagerContract
import vn.com.libertime.usermanagement.domain.service.UserService

data class RegisterParam(val userName: String, val password: String)

@KoinApiExtension
class RegisterUseCase : UseCase<RegisterParam, CredentialEntity> {
    private val userService by inject<UserService>()
    private val passwordEncryption by inject<PasswordManagerContract>()
    private val tokenProvider by inject<TokenProvider>()

    override suspend operator fun invoke(params: RegisterParam): Result<CredentialEntity> = runCatching {
        val username = params.userName
        userService.getUserByName(username)?.run {
            return@runCatching Result.Error.BusinessException("User $username has already taken")
        }
        val password = passwordEncryption.encryptPassword(params.password)
        val userId = userService.createUser(
            params.copy(password = password)
        )

        val credentials: CredentialEntity =
            tokenProvider.createTokens(Credential(userId))
        Result.Success(credentials)
    }.getOrElse {
        Result.Error.InternalSystemException(it.message)
    }
}