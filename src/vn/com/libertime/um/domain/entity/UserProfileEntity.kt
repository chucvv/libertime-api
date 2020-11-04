package vn.com.libertime.um.domain.entity

data class UserProfileEntity(
    val id: Long,
    val firebaseId: String?,
    val address: String?,
    val university: String?,
    val lat: Double?,
    val lng: Double?,
    val lastLoginDate: Long,
)