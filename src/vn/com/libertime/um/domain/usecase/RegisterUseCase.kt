package vn.com.libertime.um.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.um.data.exception.ExistedUserException
import vn.com.libertime.um.data.model.UserModel
import vn.com.libertime.um.domain.exception.ExistedStateException
import vn.com.libertime.um.domain.exception.IllegalAccessException
import vn.com.libertime.um.domain.param.RegisterParam
import vn.com.libertime.um.domain.repository.UserDao
import java.util.*

@KoinApiExtension
class RegisterUseCase : UseCase<RegisterParam, Unit> {
    private val userDao: UserDao by inject()
    override operator fun invoke(params: RegisterParam): Result<Unit> {
        userDao.getUserByName(params.userName)?.run {
            return@run Result.Error.BusinessException(ExistedStateException())
        }
        return try {
            val wasAcknowledged = userDao.createUser(
                UserModel(
                    userName = params.userName,
                    password = params.password,
                    email = params.email,
                    createdDate = Date().time
                )
            )
            if (wasAcknowledged) {
                Result.Success(Unit)
            } else {
                Result.Error.BusinessException(IllegalAccessException())
            }
        } catch (ex: ExistedUserException) {
            Result.Error.BusinessException(ExistedStateException())
        } catch (ex: Exception) {
            Result.Error.StorageException(ex)
        }
    }
}