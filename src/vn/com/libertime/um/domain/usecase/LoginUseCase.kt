package vn.com.libertime.um.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.auth.TokenProvider
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.um.domain.entity.Credentials
import vn.com.libertime.um.domain.entity.UserCredentialsEntity
import vn.com.libertime.um.domain.service.UserService
import vn.com.libertime.util.PasswordManagerContract

data class LoginParam(val userName: String, val password: String)

@KoinApiExtension
class LoginUseCase : UseCase<LoginParam, Credentials> {
    private val userService by inject<UserService>()
    private val passwordManager by inject<PasswordManagerContract>()
    private val tokenProvider by inject<TokenProvider>()

    override suspend operator fun invoke(params: LoginParam): Result<Credentials> {
        val userInfoEntity =
            userService.getUserByName(params.userName)
                ?: return Result.Error.BusinessException("User is not found")
        if (passwordManager.validatePassword(params.password, userInfoEntity.password)) {
            val credentials: Credentials =
                tokenProvider.createTokens(
                    UserCredentialsEntity(
                        userId = userInfoEntity.userId,
                        userName = userInfoEntity.userName
                    )
                )
            return Result.Success(credentials)
        }
        return Result.Error.BusinessException("Wrong password")
    }
}