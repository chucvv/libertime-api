package vn.com.libertime.um.data.dao

import com.mongodb.MongoException
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import org.litote.kmongo.updateOneById
import vn.com.libertime.shared.functions.library.exception.DatabaseException
import vn.com.libertime.um.data.model.UserModel
import vn.com.libertime.um.domain.entity.UserInfoEntity
import vn.com.libertime.um.domain.repository.DaoCreateUserParam
import vn.com.libertime.um.domain.repository.DaoUpdateUserParam
import vn.com.libertime.um.domain.repository.UserDao

class DefaultUserDao(private val userDb: MongoDatabase) : UserDao {
    private val collectionName = "user"

    private val userCollection: MongoCollection<UserModel> by lazy {
        userDb.getCollection<UserModel>(collectionName)
    }

    override suspend fun createUser(userid: Long, registerParamEntity: DaoCreateUserParam): UserInfoEntity {
        val userModel = UserModel(
            userid,
            registerParamEntity.userName,
            registerParamEntity.password,
            registerParamEntity.email
        )
        val result: InsertOneResult = try {
            userCollection.insertOne(userModel)
        } catch (ex: MongoException) {
            throw DatabaseException(ex)
        }
        if (!result.wasAcknowledged()) {
            throw DatabaseException(MongoException("Cant insert user"))
        }
        return userModel.toUserInfoEntity()
    }

    override suspend fun updateUser(userid: Long, updateUserParam: DaoUpdateUserParam): UserInfoEntity {
        val result: UpdateResult = try {
            userCollection.updateOneById(userid, updateUserParam)
        } catch (ex: MongoException) {
            throw DatabaseException(ex)
        }
        if (!result.wasAcknowledged()) {
            throw DatabaseException(MongoException("Cant update user"))
        }
        return getUserById(userid) ?: throw DatabaseException(MongoException("Cant find user"))
    }

    override suspend fun getUserByName(username: String): UserInfoEntity? =
        userCollection.findOne(UserModel::username eq username)?.toUserInfoEntity()

    override suspend fun getUserById(userid: Long): UserInfoEntity? =
        userCollection.findOne(UserModel::id eq userid)?.toUserInfoEntity()

}