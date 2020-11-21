package vn.com.libertime.adapter.server_side.service

import vn.com.libertime.adapter.server_side.database.dao.CreateUserDaoParam
import vn.com.libertime.adapter.server_side.database.dao.UpdateUserDaoParam
import vn.com.libertime.adapter.server_side.database.dao.UserDao
import vn.com.libertime.adapter.client_side.um.model.toUserEntity
import vn.com.libertime.adapter.client_side.um.model.toUserProfileEntity
import vn.com.libertime.port.um.entity.User
import vn.com.libertime.port.um.entity.UserProfile
import vn.com.libertime.port.um.required.RegisterParam
import vn.com.libertime.port.um.required.UpdateUserParam
import vn.com.libertime.port.um.required.UserRepository

class DefaultUserRepository(private val userDao: UserDao) : UserRepository {
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