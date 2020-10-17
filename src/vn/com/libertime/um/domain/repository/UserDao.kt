package vn.com.libertime.um.domain.repository

import vn.com.libertime.um.domain.entity.RegisterParam
import vn.com.libertime.um.domain.entity.UpdateUserParam
import vn.com.libertime.um.domain.entity.UserInfoEntity

interface UserDao {
    suspend fun createUser(userid: Long, registerParamEntity: RegisterParam): UserInfoEntity
    suspend fun updateUser(userid: Long, updateUserParam: UpdateUserParam): UserInfoEntity
    suspend fun getUserByName(username: String): UserInfoEntity?
    suspend fun getUserById(userid: Long): UserInfoEntity?
}