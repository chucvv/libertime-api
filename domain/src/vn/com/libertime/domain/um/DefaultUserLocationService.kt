package vn.com.libertime.domain.um

import vn.com.libertime.common.Result
import vn.com.libertime.port.um.entity.SearchUserProfile
import vn.com.libertime.port.um.provided.UserLocationService

internal class DefaultUserLocationService(private val searchInRadiusUseCase: SearchInRadiusUseCase) :
    UserLocationService {
    override suspend fun searchInRadius(userId: String, radius: Double): Result<List<SearchUserProfile>> =
        searchInRadiusUseCase(SearchInRadiusParam(userId, radius))
}