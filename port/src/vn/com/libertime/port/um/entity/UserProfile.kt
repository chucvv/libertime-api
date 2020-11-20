package vn.com.libertime.port.um.entity

data class UserProfile(
    val id: String,
    val firebaseId: String?,
    val address: String?,
    val university: String?,
    val lat: Double?,
    val lng: Double?,
    val lastLoginDate: Long,
)