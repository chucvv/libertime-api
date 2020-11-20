package vn.com.libertime.usermanagement.domain.entity

import io.ktor.auth.*

data class Credential(val userId: String) : Principal