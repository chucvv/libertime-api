package vn.com.libertime.um.domain.repository

import vn.com.libertime.um.data.model.UserModel

interface UserDao {
    suspend fun createUser(userModel: UserModel): UserModel
    suspend fun updateUser(userModel: UserModel): UserModel
    suspend fun getUserByName(userName: String): UserModel?
    suspend fun getUserById(userId: String): UserModel?
}