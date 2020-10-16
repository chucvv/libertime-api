package vn.com.libertime.um.domain.entity

import io.ktor.auth.Principal

data class UserEntity(val userId: String, val userName: String, val password: String, val email: String?) : Principal