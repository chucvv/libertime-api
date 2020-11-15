package vn.com.libertime.um.data.service

import vn.com.libertime.um.data.service.dao.CreateUserDaoParam
import vn.com.libertime.um.data.service.dao.UpdateUserDaoParam
import vn.com.libertime.um.data.service.dao.UserDao
import vn.com.libertime.um.domain.entity.UserEntity
import vn.com.libertime.um.domain.entity.UserInfoEntity
import vn.com.libertime.um.domain.entity.UserProfileEntity
import vn.com.libertime.um.domain.service.UserService
import vn.com.libertime.um.domain.usecase.RegisterParam
import vn.com.libertime.um.domain.usecase.UpdateUserParam

class DefaultUserService(private val userDao: UserDao) : UserService {
    override suspend fun createUser(registerParam: RegisterParam): UserEntity? =
        userDao.createUser(registerParam.userid, CreateUserDaoParam.fromRegisterParam(registerParam))

    override suspend fun updateUser(updateUserParam: UpdateUserParam): UserInfoEntity? =
        userDao.updateUser(updateUserParam.userId, UpdateUserDaoParam.fromUpdateUserParam(updateUserParam))

    override suspend fun getUserByName(username: String): UserEntity? = userDao.getUserByName(username)

    override suspend fun getUserById(userid: Long): UserEntity? = userDao.getUserById(userid)

    override suspend fun getUserProfileById(userid: Long): UserProfileEntity? = userDao.getUserProfileById(userid)
}