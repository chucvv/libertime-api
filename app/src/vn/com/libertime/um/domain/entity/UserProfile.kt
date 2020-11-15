package vn.com.libertime.um.domain.entity

data class UserProfile(
    val id: String,
    val firebaseId: String?,
    val address: String?,
    val university: String?,
    val lat: Double?,
    val lng: Double?,
    val lastLoginDate: Long,
)