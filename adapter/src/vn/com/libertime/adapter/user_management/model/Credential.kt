package vn.com.libertime.adapter.user_management.model

import io.ktor.auth.*
import vn.com.libertime.port.provided.entity.User

data class Credential(val userId: String) : Principal

fun User.toCredential() = Credential(userId)