package vn.com.libertime.controller

import vn.com.libertime.domain.entity.User
import io.ktor.application.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

fun Route.userRoutes() {
    val logger: Logger = LoggerFactory.getLogger("UserController")
    val userDb: CoroutineDatabase by inject()
    val collectionName = "user"
    route("/user") {
        post("/register") {
            val parameters = call.receiveParameters()
            val userName = parameters["username"] ?: run {
                call.respond(BadRequest, "Need user name")
                return@post
            }
            val password = parameters["password"] ?: run {
                call.respond(BadRequest, "Need password")
                return@post
            }

            val userCollection = userDb.getCollection<User>(collectionName)

            userCollection.findOne(User::userName eq userName)?.run {
                call.respond(BadRequest, "Existed")
                return@post
            }

            userCollection.insertOne(
                User(
                    userName = userName,
                    password = password,
                    email = "",
                    createdDate = Date().time
                )
            )
            call.respond(OK, "Wellcome $userName")
            logger.info("Register user success [$userName]")
        }

        post("/login") {
            val parameters = call.receiveParameters()
            val userName = parameters["username"] ?: run {
                call.respond(BadRequest, "Need user name")
                return@post
            }
            val password = parameters["password"] ?: run {
                call.respond(BadRequest, "Need password")
                return@post
            }
            logger.info("User [$userName] login")
            val userCollection = userDb.getCollection<User>(collectionName)
            userCollection.findOne(User::userName eq userName, User::password eq password)?.let {
                call.respond(OK)
            } ?: call.respond(Unauthorized)
        }
    }
}