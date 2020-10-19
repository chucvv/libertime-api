package vn.com.libertime.um.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.um.domain.entity.RegisterParam
import vn.com.libertime.um.domain.entity.UserInfoEntity
import vn.com.libertime.um.domain.repository.DaoCreateUserParam
import vn.com.libertime.um.domain.repository.UserDao
import vn.com.libertime.util.Number
import vn.com.libertime.util.PasswordManagerContract

@KoinApiExtension
class RegisterUseCase : UseCase<RegisterParam, UserInfoEntity> {
    private val userDao by inject<UserDao>()
    private val passwordEncryption by inject<PasswordManagerContract>()
    override suspend operator fun invoke(params: RegisterParam): Result<UserInfoEntity> =
        try {
            userDao.getUserByName(params.userName)?.run {
                return Result.Error.BusinessException("User is existed")
            }
            val password = passwordEncryption.encryptPassword(params.password)
            val userId: Long = Number.generateUniqueNumber()
            val userInfoEntity = userDao.createUser(
                userId,
                DaoCreateUserParam(
                    userName = params.userName, password = password, email = params.email
                )
            )
            Result.Success(userInfoEntity)
        } catch (ex: Exception) {
            Result.Error.StorageException(ex.message)
        }

}