package vn.com.libertime.database

import vn.com.libertime.database.entity.EntityUser
import vn.com.libertime.database.entity.EntityUserProfile
import vn.com.libertime.port.um.entity.User
import vn.com.libertime.port.um.entity.UserProfile

internal fun EntityUser.toUserEntity() = User(
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

internal fun EntityUserProfile.toUserProfileEntity() = UserProfile(
    id = id.value.toString(),
    firebaseId = firebaseId,
    address = address,
    university = university,
    lat = location?.x,
    lng = location?.y,
    lastLoginDate = lastLoginDate.millis,
)