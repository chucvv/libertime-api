package vn.com.libertime.adapter.client_side.um.model

import io.ktor.auth.*
import vn.com.libertime.port.um.entity.User

internal data class Credential(val userId: String) : Principal

internal fun User.toCredential() = Credential(userId)