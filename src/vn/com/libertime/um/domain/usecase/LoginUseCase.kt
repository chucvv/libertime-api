package vn.com.libertime.um.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.auth.TokenProvider
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.um.domain.entity.LoginEntity
import vn.com.libertime.um.domain.entity.UserEntity
import vn.com.libertime.um.domain.exception.NotFoundException
import vn.com.libertime.um.domain.repository.UserDao
import vn.com.libertime.um.presentation.model.Credentials
import vn.com.libertime.util.PasswordManagerContract

@KoinApiExtension
class LoginUseCase : UseCase<LoginEntity, Credentials> {
    private val userDao by inject<UserDao>()
    private val passwordManager by inject<PasswordManagerContract>()
    private val tokenProvider by inject<TokenProvider>()

    override suspend operator fun invoke(params: LoginEntity): Result<Credentials> {
        val userModel =
            userDao.getUserByName(params.userName)
                ?: return Result.Error.BusinessException(NotFoundException("User is not found"))
        if (passwordManager.validatePassword(params.password, userModel.password)) {
            val credentials: Credentials =
                tokenProvider.createTokens(
                    UserEntity(
                        userId = userModel.id,
                        userName = userModel.userName,
                        password = userModel.password,
                        email = userModel.email
                    )
                )
            return Result.Success(credentials)
        }
        return Result.Error.BusinessException(IllegalAccessException())
    }
}