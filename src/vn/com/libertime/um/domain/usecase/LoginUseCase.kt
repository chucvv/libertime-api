package vn.com.libertime.um.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.auth.TokenProvider
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.um.domain.entity.Credentials
import vn.com.libertime.um.domain.entity.LoginParam
import vn.com.libertime.um.domain.entity.UserCredentialsEntity
import vn.com.libertime.um.domain.exception.NotFoundException
import vn.com.libertime.um.domain.repository.UserDao
import vn.com.libertime.util.PasswordManagerContract

@KoinApiExtension
class LoginUseCase : UseCase<LoginParam, Credentials> {
    private val userDao by inject<UserDao>()
    private val passwordManager by inject<PasswordManagerContract>()
    private val tokenProvider by inject<TokenProvider>()

    override suspend operator fun invoke(params: LoginParam): Result<Credentials> {
        val userInfoEntity =
            userDao.getUserByName(params.userName)
                ?: return Result.Error.BusinessException(NotFoundException("User is not found"))
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
        return Result.Error.BusinessException(IllegalAccessException())
    }
}