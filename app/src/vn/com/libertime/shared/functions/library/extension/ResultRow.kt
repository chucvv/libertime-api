package vn.com.libertime.shared.functions.library.extension

import org.jetbrains.exposed.sql.ResultRow
import vn.com.libertime.um.data.model.UserProfile
import vn.com.libertime.um.data.model.Users
import vn.com.libertime.um.domain.entity.UserEntity
import vn.com.libertime.um.domain.entity.UserProfileEntity

fun ResultRow.toUserEntity() = UserEntity(
    userId = get(Users.id),
    username = get(Users.username),
    password = get(Users.password),
    firstname = get(Users.firstname),
    lastname = get(Users.lastname),
    email = get(Users.email),
    sex = get(Users.sex),
    birthday = get(Users.birthday),
    createdDate = get(Users.createdDate),
)

fun ResultRow.toUserProfileEntity() = UserProfileEntity(
    id = get(UserProfile.id),
    firebaseId = get(UserProfile.firebaseId),
    address = get(UserProfile.address),
    university = get(UserProfile.university),
    lat = get(UserProfile.lat),
    lng = get(UserProfile.lng),
    lastLoginDate = get(UserProfile.lastLoginDate),
)