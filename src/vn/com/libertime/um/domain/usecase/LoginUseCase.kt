package vn.com.libertime.um.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.um.domain.exception.NotFoundException
import vn.com.libertime.um.domain.param.LoginParam
import vn.com.libertime.um.domain.repository.UserDao

@KoinApiExtension
class LoginUseCase : UseCase<LoginParam, Boolean> {
    private val userDao: UserDao by inject()
    override operator fun invoke(params: LoginParam): Result<Boolean> {
        val user =
            userDao.getUserByName(params.userName) ?: return Result.Error.BusinessException(NotFoundException())
        if (user.password != params.password) {
            return Result.Error.BusinessException(IllegalAccessException())
        }
        return Result.Success(true)
    }
}