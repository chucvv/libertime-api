package vn.com.libertime.um.domain.repository

import vn.com.libertime.um.domain.entity.UserInfoEntity

interface UserDao {
    suspend fun createUser(userid: Long, registerParamEntity: DaoCreateUserParam): UserInfoEntity
    suspend fun updateUser(userid: Long, updateUserParam: DaoUpdateUserParam): UserInfoEntity
    suspend fun getUserByName(username: String): UserInfoEntity?
    suspend fun getUserById(userid: Long): UserInfoEntity?
}

data class DaoUpdateUserParam(val userName: String?, val password: String?, val email: String?)
data class DaoCreateUserParam(val userName: String, val password: String, val email: String?)