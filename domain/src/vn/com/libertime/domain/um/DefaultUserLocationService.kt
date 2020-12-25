package vn.com.libertime.domain.um

import vn.com.libertime.common.Result
import vn.com.libertime.port.um.entity.UserProfile
import vn.com.libertime.port.um.provided.UserLocationService

internal class DefaultUserLocationService(private val searchInRadiusUseCase: SearchInRadiusUseCase) :
    UserLocationService {
    override suspend fun searchInRadius(userId: String, radius: Double): Result<List<UserProfile>> =
        searchInRadiusUseCase(SearchInRadiusParam(userId, radius))
}