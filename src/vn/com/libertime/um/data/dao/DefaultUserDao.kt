package vn.com.libertime.um.data.dao

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import vn.com.libertime.shared.functions.library.exception.DatabaseException
import vn.com.libertime.um.data.extension.toUserEntity
import vn.com.libertime.um.data.model.Users
import vn.com.libertime.um.data.model.Users.userId
import vn.com.libertime.um.domain.entity.UserInfoEntity
import vn.com.libertime.um.domain.repository.DaoCreateUserParam
import vn.com.libertime.um.domain.repository.DaoUpdateUserParam
import vn.com.libertime.um.domain.repository.UserDao

class DefaultUserDao : UserDao {

    init {
        transaction {
            SchemaUtils.create(Users)
        }
    }

    override suspend fun createUser(userid: Long, registerParamEntity: DaoCreateUserParam): UserInfoEntity {
        return try {
            transaction {
                Users.insert {
                    it[userId] = userid
                    it[username] = registerParamEntity.userName
                    it[password] = registerParamEntity.password
                    it[email] = registerParamEntity.email
                }
                Users.select { userId eq userid }.single().toUserEntity()
            }
        } catch (ex: Exception) {
            throw DatabaseException(ex)
        }
    }

    override suspend fun updateUser(userid: Long, updateUserParam: DaoUpdateUserParam): UserInfoEntity {
        return try {
            transaction {
                Users.update({ userId eq userid }) {
                    updateUserParam.userName?.run {
                        it[username] = updateUserParam.userName
                    }
                    updateUserParam.email?.run {
                        it[email] = updateUserParam.email
                    }
                }
                Users.select { userId eq userid }.single().toUserEntity()
            }
        } catch (ex: Exception) {
            throw DatabaseException(ex)
        }
    }

    override suspend fun getUserByName(username: String): UserInfoEntity? = transaction {
        Users.select { Users.username eq username }.singleOrNull()?.toUserEntity()
    }

    override suspend fun getUserById(userid: Long): UserInfoEntity? = transaction {
        Users.select { userId eq userid }.singleOrNull()?.toUserEntity()
    }

}