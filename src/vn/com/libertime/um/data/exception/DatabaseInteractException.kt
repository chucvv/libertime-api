package vn.com.libertime.um.data.exception

import com.mongodb.MongoException

class DatabaseInteractException(exception: MongoException) : Exception()