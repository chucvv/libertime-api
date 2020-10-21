package vn.com.libertime.um.data.service

import vn.com.libertime.um.data.service.dao.UserDao
import vn.com.libertime.um.domain.entity.UserInfoEntity
import vn.com.libertime.um.domain.service.CreateUserDaoParam
import vn.com.libertime.um.domain.service.UpdateUserDaoParam
import vn.com.libertime.um.domain.service.UserService

class DefaultUserService(private val userDao: UserDao) : UserService {
    override suspend fun createUser(userid: Long, registerParamEntity: CreateUserDaoParam): UserInfoEntity? =
        userDao.createUser(userid, registerParamEntity)

    override suspend fun updateUser(userid: Long, updateUserParam: UpdateUserDaoParam): UserInfoEntity? =
        userDao.updateUser(userid, updateUserParam)

    override suspend fun getUserByName(username: String): UserInfoEntity? = userDao.getUserByName(username)

    override suspend fun getUserById(userid: Long): UserInfoEntity? = userDao.getUserById(userid)
}