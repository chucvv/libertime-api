package vn.com.libertime.um.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.um.data.model.UserModel
import vn.com.libertime.um.domain.entity.RegisterEntity
import vn.com.libertime.um.domain.exception.ExistedStateException
import vn.com.libertime.um.domain.repository.UserDao
import vn.com.libertime.util.PasswordManagerContract
import java.util.*

@KoinApiExtension
class RegisterUseCase : UseCase<RegisterEntity, Unit> {
    private val userDao by inject<UserDao>()
    private val passwordEncryption by inject<PasswordManagerContract>()
    override suspend operator fun invoke(params: RegisterEntity): Result<Unit> {
        userDao.getUserByName(params.userName)?.run {
            return@run Result.Error.BusinessException(ExistedStateException("User is existed"))
        }
        val encryptedPassword = passwordEncryption.encryptPassword(params.password)
        return try {
            userDao.createUser(
                UserModel(
                    id = "",
                    userName = params.userName,
                    password = encryptedPassword,
                    email = params.email,
                    createdDate = Date().time

                )
            )
            Result.Success(Unit)
        } catch (ex: Exception) {
            Result.Error.StorageException(ex)
        }
    }
}