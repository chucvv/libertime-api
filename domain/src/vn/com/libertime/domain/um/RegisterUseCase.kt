package vn.com.libertime.domain.um

import vn.com.libertime.common.Result
import vn.com.libertime.common.UseCase
import vn.com.libertime.port.um.entity.CredentialEntity
import vn.com.libertime.port.um.entity.UserEvent
import vn.com.libertime.port.um.provided.RegisterParam
import vn.com.libertime.port.um.required.*

internal class RegisterUseCase(
    private val userDao: UserDao,
    private val encryptedPassword: PasswordEncryptable,
    private val tokenProvider: TokenProvidable,
    private val userNotificationPort: UserNotificationPort
) : UseCase<RegisterParam, CredentialEntity> {

    override suspend operator fun invoke(params: RegisterParam): Result<CredentialEntity> = runCatching {
        val username = params.userName
        userDao.getUserByName(username)?.run {
            return@runCatching Result.Error.BusinessException("User $username has already taken")
        }
        val password = encryptedPassword.encryptPassword(params.password)
        val user = userDao.createUser(
            UserRegisterParam(username = username, password = password)
        )

        val credentials: CredentialEntity =
            tokenProvider.createTokens(user.userId)

        userNotificationPort.notify(userEvent = UserEvent(user = user))

        Result.Success(credentials)
    }.getOrElse {
        Result.Error.InternalSystemException(it.message)
    }
}