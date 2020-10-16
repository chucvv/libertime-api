package vn.com.libertime.um.presentation.model

import io.ktor.auth.*

data class User(val userId: String, val userName: String, val password: String, val email: String?) : Principal