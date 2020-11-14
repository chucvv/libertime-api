package vn.com.libertime.um.domain.entity

import io.ktor.auth.Principal

data class Credential(val userId: Long) : Principal