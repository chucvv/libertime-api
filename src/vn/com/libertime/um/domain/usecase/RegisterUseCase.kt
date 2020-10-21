package vn.com.libertime.um.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.um.domain.entity.UserInfoEntity
import vn.com.libertime.um.domain.service.CreateUserDaoParam
import vn.com.libertime.um.domain.service.UserService
import vn.com.libertime.util.Number
import vn.com.libertime.util.PasswordManagerContract

data class RegisterParam(val userName: String, val password: String, val email: String?)

@KoinApiExtension
class RegisterUseCase : UseCase<RegisterParam, UserInfoEntity> {
    private val userService by inject<UserService>()
    private val passwordEncryption by inject<PasswordManagerContract>()
    override suspend operator fun invoke(params: RegisterParam): Result<UserInfoEntity> {
        try {
            userService.getUserByName(params.userName)?.run {
                return Result.Error.BusinessException("User has already taken")
            }
            val password = passwordEncryption.encryptPassword(params.password)
            val userId: Long = Number.generateUniqueNumber()
            val userInfoEntity: UserInfoEntity = userService.createUser(
                userId,
                CreateUserDaoParam(
                    userName = params.userName, password = password, email = params.email
                )
            ) ?: return Result.Error.BusinessException("User created unsuccessfully")
            return Result.Success(userInfoEntity)
        } catch (ex: Exception) {
            return Result.Error.InternalSystemException(ex.message)
        }
    }
}