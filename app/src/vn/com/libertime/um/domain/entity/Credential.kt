package vn.com.libertime.um.domain.entity

import io.ktor.auth.*

data class Credential(val userId: String) : Principal