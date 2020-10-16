package vn.com.libertime.um.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.um.domain.entity.UserEntity
import vn.com.libertime.um.domain.exception.NotFoundException
import vn.com.libertime.um.domain.repository.UserDao

@KoinApiExtension
class GetUserByIdUseCase : UseCase<String, UserEntity> {
    private val userDao by inject<UserDao>()
    override suspend operator fun invoke(userId: String): Result<UserEntity> {
        val user = userDao.getUserById(userId) ?: return Result.Error.BusinessException(NotFoundException())
        return Result.Success(user.toUserEntity())
    }
}