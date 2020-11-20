package vn.com.libertime.usermanagement.domain.service

import vn.com.libertime.usermanagement.domain.entity.User
import vn.com.libertime.usermanagement.domain.entity.UserProfile
import vn.com.libertime.usermanagement.domain.usecase.RegisterParam
import vn.com.libertime.usermanagement.domain.usecase.UpdateUserParam

interface UserService {
    suspend fun createUser(registerParam: RegisterParam): String
    suspend fun updateUser(updateUserParam: UpdateUserParam): String
    suspend fun getUserByName(username: String): User?
    suspend fun getUserById(userid: String): User?
    suspend fun getUserProfileById(userid: String): UserProfile?
}