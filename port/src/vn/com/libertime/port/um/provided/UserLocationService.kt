package vn.com.libertime.port.um.provided

import vn.com.libertime.common.Result
import vn.com.libertime.port.um.entity.UserProfile

public interface UserLocationService {
    public suspend fun searchInRadius(userId: String, radius: Double): Result<List<UserProfile>>
}