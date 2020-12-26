package vn.com.libertime.port.um.entity

public data class SearchUserProfile(
    val id: String,
    val address: String?,
    val university: String?,
    val lat: Double?,
    val lng: Double?,
    val distance: Double = 0.0,
    val lastLoginDate: Long,
)