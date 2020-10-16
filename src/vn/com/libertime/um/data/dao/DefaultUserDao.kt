package vn.com.libertime.um.data.dao

import com.mongodb.MongoException
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.InsertOneResult
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import org.litote.kmongo.updateOneById
import vn.com.libertime.um.data.exception.DatabaseInteractException
import vn.com.libertime.um.data.model.UserModel
import vn.com.libertime.um.domain.repository.UserDao

class DefaultUserDao(private val userDb: MongoDatabase) : UserDao {
    private val collectionName = "user"

    private val userCollection: MongoCollection<UserModel> by lazy {
        userDb.getCollection<UserModel>(collectionName)
    }

    override suspend fun createUser(userModel: UserModel): UserModel {
        val result: InsertOneResult = try {
            userCollection.insertOne(userModel)
        } catch (ex: MongoException) {
            throw DatabaseInteractException(ex)
        }
        if (!result.wasAcknowledged()) {
            throw DatabaseInteractException(MongoException("Cant insert user"))
        }
        return userModel
    }

    override suspend fun updateUser(userModel: UserModel): UserModel {
        val result = try {
            userCollection.updateOneById(userModel.id, userModel)
        } catch (ex: MongoException) {
            throw DatabaseInteractException(ex)
        }
        if (result.modifiedCount <= 0) {
            throw DatabaseInteractException(MongoException("Cant update user"))
        }
        return userModel
    }

    override suspend fun getUserByName(userName: String): UserModel? =
        userCollection.findOne(UserModel::userName eq userName)

    override suspend fun getUserById(userId: String): UserModel? = userCollection.findOne(UserModel::id eq userId)

}