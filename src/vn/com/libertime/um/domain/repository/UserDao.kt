package vn.com.libertime.um.domain.repository

import vn.com.libertime.um.data.model.UserModel

interface UserDao {
    fun createUser(userEntity: UserModel): Boolean
    fun updateUser(userEntity: UserModel)
    fun getUserByName(userName: String): UserModel?
    fun getUserById(userId: String): UserModel?
}