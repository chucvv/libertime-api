package vn.com.libertime.port.um.required

import vn.com.libertime.port.um.entity.UserProfile

public interface UserLocationRepository {
    public suspend fun searchInRadius(centerLat: Double, centerLng: Double, radius: Double): List<UserProfile>
}