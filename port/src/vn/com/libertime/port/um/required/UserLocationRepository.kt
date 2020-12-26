package vn.com.libertime.port.um.required

import vn.com.libertime.port.um.entity.SearchUserProfile

public interface UserLocationRepository {
    public suspend fun searchInRadius(centerLat: Double, centerLng: Double, radius: Double): List<SearchUserProfile>
}