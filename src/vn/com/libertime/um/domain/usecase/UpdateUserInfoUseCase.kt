package vn.com.libertime.um.domain.usecase

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import vn.com.libertime.shared.functions.library.Result
import vn.com.libertime.shared.functions.library.UseCase
import vn.com.libertime.um.domain.entity.UpdateUserParam
import vn.com.libertime.um.domain.entity.UserInfoEntity
import vn.com.libertime.um.domain.repository.DaoUpdateUserParam
import vn.com.libertime.um.domain.repository.UserDao

@KoinApiExtension
class UpdateUserInfoUseCase : UseCase<UpdateUserParam, UserInfoEntity> {
    private val userDao by inject<UserDao>()
    override suspend operator fun invoke(params: UpdateUserParam): Result<UserInfoEntity> =
        try {
            val userId = params.userId
            userDao.getUserById(userId) ?: Result.Error.BusinessException("User is not found")
            val user = userDao.updateUser(
                userId,
                DaoUpdateUserParam(userName = params.username, email = params.email)
            )
            Result.Success(user)
        } catch (ex: Exception) {
            Result.Error.StorageException(ex.message)
        }

}