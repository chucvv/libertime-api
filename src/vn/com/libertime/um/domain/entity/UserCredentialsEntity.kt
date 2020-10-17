package vn.com.libertime.um.domain.entity

import io.ktor.auth.Principal

data class UserCredentialsEntity(val userId: Long, val userName: String) : Principal