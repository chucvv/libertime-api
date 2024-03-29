package vn.com.libertime.adapter.client_side.um.response

import vn.com.libertime.port.um.entity.UserInfo

public data class MeResponse(
    val userId: String,
    val username: String,
    val email: String?,
    val firstname: String?,
    val lastname: String?,
    val sex: Boolean,
    val birthday: Long,
    val firebaseId: String?,
    val address: String?,
    val university: String?,
    val lat: Double?,
    val lng: Double?,
) {
    public companion object {
        public fun fromUserInfoEntity(userInfoEntity: UserInfo): MeResponse {
            val userInfo = userInfoEntity.user
            val userProfile = userInfoEntity.userProfile
            return MeResponse(
                userId = userInfo.userId,
                username = userInfo.username,
                email = userInfo.email,
                firstname = userInfo.firstname,
                lastname = userInfo.lastname,
                sex = userInfo.sex,
                birthday = userInfo.birthday,
                firebaseId = userProfile?.firebaseId,
                address = userProfile?.address,
                university = userProfile?.university,
                lat = userProfile?.lat,
                lng = userProfile?.lng,
            )
        }
    }
}