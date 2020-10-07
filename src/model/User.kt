package com.example.model

import org.bson.codecs.pojo.annotations.BsonId
import java.util.*

data class User(
    @BsonId val id: UUID = UUID.randomUUID(),
    val userName: String,
    val password: String,
    val email: String,
    val createdDate: Long
)