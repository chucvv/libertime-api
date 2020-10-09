package vn.com.libertime.um.data.model

import org.bson.codecs.pojo.annotations.BsonId
import java.util.*

data class UserModel(
    @BsonId val id: UUID = UUID.randomUUID(),
    val userName: String,
    val password: String,
    val email: String,
    val createdDate: Long
)