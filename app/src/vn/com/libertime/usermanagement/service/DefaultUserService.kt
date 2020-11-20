package vn.com.libertime.usermanagement.service

import vn.com.libertime.dao.CreateUserDaoParam
import vn.com.libertime.dao.UpdateUserDaoParam
import vn.com.libertime.dao.UserDao
import vn.com.libertime.usermanagement.domain.entity.User
import vn.com.libertime.usermanagement.domain.entity.UserProfile
import vn.com.libertime.usermanagement.domain.extension.toUserEntity
import vn.com.libertime.usermanagement.domain.extension.toUserProfileEntity
import vn.com.libertime.usermanagement.domain.service.UserService
import vn.com.libertime.usermanagement.domain.usecase.RegisterParam
import vn.com.libertime.usermanagement.domain.usecase.UpdateUserParam

class DefaultUserService(private val userDao: UserDao) : UserService {
    override suspend fun createUser(registerParam: RegisterParam): String =
        userDao.createUser(toUserDaoParam(registerParam))

    override suspend fun updateUser(updateUserParam: UpdateUserParam): String =
        userDao.updateUser(updateUserParam.userId, toUserDaoParam(updateUserParam))

    override suspend fun getUserByName(username: String): User? = userDao.getUserByName(username)?.toUserEntity()

    override suspend fun getUserById(userid: String): User? = userDao.getUserById(userid)?.toUserEntity()

    override suspend fun getUserProfileById(userid: String): UserProfile? =
        userDao.getUserProfileById(userid)?.toUserProfileEntity()
}

fun toUserDaoParam(updateUserParam: UpdateUserParam): UpdateUserDaoParam = UpdateUserDaoParam(
    email = updateUserParam.email,
    firstname = updateUserParam.firstname,
    lastname = updateUserParam.lastname,
    sex = updateUserParam.sex,
    birthday = updateUserParam.birthday,
    firebaseId = updateUserParam.firebaseId,
    address = updateUserParam.address,
    university = updateUserParam.university,
    lat = updateUserParam.lat,
    lng = updateUserParam.lng
)

fun toUserDaoParam(registerParam: RegisterParam): CreateUserDaoParam = CreateUserDaoParam(
    username = registerParam.userName,
    password = registerParam.password,
)