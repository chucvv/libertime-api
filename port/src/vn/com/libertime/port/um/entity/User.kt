package vn.com.libertime.port.um.entity

public data class User(
    val userId: String,
    val username: String,
    val password: String,
    val firstname: String?,
    val lastname: String?,
    val email: String?,
    val sex: Boolean,
    val birthday: Long,
    val createdDate: Long
)