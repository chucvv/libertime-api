package vn.com.libertime.um.domain.extension

import vn.com.libertime.entity.EntityUser
import vn.com.libertime.entity.EntityUserProfile
import vn.com.libertime.um.domain.entity.User
import vn.com.libertime.um.domain.entity.UserProfile

fun EntityUser.toUserEntity() = User(
    userId = id.value.toString(),
    username = username,
    password = password,
    firstname = firstname,
    lastname = lastname,
    email = email,
    sex = sex,
    birthday = birthday,
    createdDate = createdDate.millis,
)

fun EntityUserProfile.toUserProfileEntity() = UserProfile(
    id = id.value.toString(),
    firebaseId = firebaseId,
    address = address,
    university = university,
    lat = lat,
    lng = lng,
    lastLoginDate = lastLoginDate.millis,
)