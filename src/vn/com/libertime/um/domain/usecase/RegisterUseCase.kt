package vn.com.libertime.um.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.auth.TokenProvider
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.um.domain.entity.Credential
import vn.com.libertime.um.domain.entity.CredentialEntity
import vn.com.libertime.um.domain.service.PasswordManagerContract
import vn.com.libertime.um.domain.service.UserService
import vn.com.libertime.utilities.Number

data class RegisterParam(val userid: Long = 0, val userName: String, val password: String)

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
        val userId: Long = Number.generateUniqueNumber()
        userService.createUser(
            params.copy(userid = userId, password = password)
        ) ?: return Result.Error.BusinessException("User $username created unsuccessfully")

        val credentials: CredentialEntity =
            tokenProvider.createTokens(Credential(userId))
        Result.Success(credentials)
    }.getOrElse {
        Result.Error.InternalSystemException(it.message)
    }
}