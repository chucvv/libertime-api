package vn.com.libertime.um.domain.service

import vn.com.libertime.um.domain.entity.UserInfoEntity

interface UserService {
    suspend fun createUser(userid: Long, registerParamEntity: CreateUserDaoParam): UserInfoEntity?
    suspend fun updateUser(userid: Long, updateUserParam: UpdateUserDaoParam): UserInfoEntity?
    suspend fun getUserByName(username: String): UserInfoEntity?
    suspend fun getUserById(userid: Long): UserInfoEntity?
}

data class UpdateUserDaoParam(val userName: String?, val email: String?)
data class CreateUserDaoParam(val userName: String, val password: String, val email: String?)