package vn.com.libertime.um.presentation.model.request

import vn.com.libertime.um.domain.usecase.UpdateUserParam

data class UpdateProfileRequest(
    val email: String,
    val firstname: String,
    val lastname: String,
    val sex: Boolean,
    val birthday: Long,
    val firebaseId: String,
    val address: String,
    val university: String,
    val lat: Double,
    val lng: Double,
) {
    fun toUpdateUserParam(userid: String): UpdateUserParam = UpdateUserParam(
        userId = userid,
        email = email,
        firstname = firstname,
        lastname = lastname,
        sex = sex,
        birthday = birthday,
        firebaseId = firebaseId,
        address = address,
        university = university,
        lat = lat,
        lng = lng,
    )
}