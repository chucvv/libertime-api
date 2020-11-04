package vn.com.libertime.um.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.um.domain.entity.UserEntity
import vn.com.libertime.um.domain.service.UserService
import vn.com.libertime.util.Number
import vn.com.libertime.util.PasswordManagerContract

data class RegisterParam(val userid: Long, val userName: String, val password: String, val email: String?)

@KoinApiExtension
class RegisterUseCase : UseCase<RegisterParam, UserEntity> {
    private val userService by inject<UserService>()
    private val passwordEncryption by inject<PasswordManagerContract>()
    override suspend operator fun invoke(params: RegisterParam): Result<UserEntity> {
        try {
            val username = params.userName
            userService.getUserByName(username)?.run {
                return Result.Error.BusinessException("User $username has already taken")
            }
            val password = passwordEncryption.encryptPassword(params.password)
            val userId: Long = Number.generateUniqueNumber()
            val userInfoEntity: UserEntity = userService.createUser(
                params.copy(userid = userId, password = password)
            ) ?: return Result.Error.BusinessException("User $username created unsuccessfully")
            return Result.Success(userInfoEntity)
        } catch (ex: Exception) {
            return Result.Error.InternalSystemException(ex.message)
        }
    }
}