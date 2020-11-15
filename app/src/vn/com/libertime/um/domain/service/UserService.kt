package vn.com.libertime.um.domain.service

import vn.com.libertime.um.domain.entity.UserEntity
import vn.com.libertime.um.domain.entity.UserInfoEntity
import vn.com.libertime.um.domain.entity.UserProfileEntity
import vn.com.libertime.um.domain.usecase.RegisterParam
import vn.com.libertime.um.domain.usecase.UpdateUserParam

interface UserService {
    suspend fun createUser(registerParam: RegisterParam): UserEntity?
    suspend fun updateUser(updateUserParam: UpdateUserParam): UserInfoEntity?
    suspend fun getUserByName(username: String): UserEntity?
    suspend fun getUserById(userid: Long): UserEntity?
    suspend fun getUserProfileById(userid: Long): UserProfileEntity?
}