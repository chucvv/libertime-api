package vn.com.libertime.adapter.um.model

import io.ktor.auth.*
import vn.com.libertime.port.um.entity.User

data class Credential(val userId: String) : Principal

fun User.toCredential() = Credential(userId)