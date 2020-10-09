package vn.com.libertime.um.data.dao

import com.mongodb.MongoException
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.InsertOneResult
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import org.litote.kmongo.updateOneById
import vn.com.libertime.um.data.exception.DatabaseInteractException
import vn.com.libertime.um.data.exception.ExistedUserException
import vn.com.libertime.um.data.model.UserModel
import vn.com.libertime.um.domain.repository.UserDao

@KoinApiExtension
class DefaultUserDao(private val userDb: MongoDatabase) : UserDao, KoinComponent {
    private val collectionName = "user"

    private val userCollection: MongoCollection<UserModel> by lazy {
        userDb.getCollection<UserModel>(collectionName)
    }

    override fun createUser(userEntity: UserModel): Boolean {
        getUserByName(userName = userEntity.userName)?.run {
            throw ExistedUserException()
        }
        val result: InsertOneResult = try {
            userCollection.insertOne(userEntity)
        } catch (ex: MongoException) {
            throw DatabaseInteractException(ex)
        }
        return result.wasAcknowledged()
    }

    override fun updateUser(userEntity: UserModel) {
        try {
            userCollection.updateOneById(userEntity.id, userEntity)
        } catch (ex: MongoException) {
            throw DatabaseInteractException(ex)
        }

    }

    override fun getUserByName(userName: String): UserModel? = userCollection.findOne(UserModel::userName eq userName)
    override fun getUserById(userId: String): UserModel? = userCollection.findOne(UserModel::id eq userId)

}