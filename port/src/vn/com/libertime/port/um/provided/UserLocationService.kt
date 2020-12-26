package vn.com.libertime.port.um.provided

import vn.com.libertime.common.Result
import vn.com.libertime.port.um.entity.SearchUserProfile

public interface UserLocationService {
    public suspend fun searchInRadius(userId: String, radius: Double): Result<List<SearchUserProfile>>
}