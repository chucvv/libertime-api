package vn.com.libertime.um.domain.entity

data class UserEntity(
    val userId: Long,
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