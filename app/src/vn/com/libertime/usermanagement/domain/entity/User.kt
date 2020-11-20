package vn.com.libertime.usermanagement.domain.entity

data class User(
    val userId: String,
    val username: String,
    val password: String,
    val firstname: String?,
    val lastname: String?,
    val email: String?,
    val sex: Boolean,
    val birthday: Long,
    val createdDate: Long
) {
    fun toUserCredentialsEntity() = Credential(userId)
}