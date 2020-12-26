package vn.com.libertime.domain.um

import vn.com.libertime.common.Result
import vn.com.libertime.common.UseCase
import vn.com.libertime.port.um.entity.SearchUserProfile
import vn.com.libertime.port.um.required.UserLocationRepository
import vn.com.libertime.port.um.required.UserRepository

internal data class SearchInRadiusParam(val userId: String, val radius: Double)

internal class SearchInRadiusUseCase(
    private val userRepository: UserRepository,
    private val userLocationRepository: UserLocationRepository
) :
    UseCase<SearchInRadiusParam, List<SearchUserProfile>> {
    override suspend operator fun invoke(params: SearchInRadiusParam): Result<List<SearchUserProfile>> {
        val userProfile = userRepository.getUserProfileById(params.userId)
            ?: return Result.Error.BusinessException("User is not found")
        return userProfile.takeIf { it.lat != null && it.lng != null }?.let {
            val result = userLocationRepository.searchInRadius(it.lat ?: 0.0, it.lng ?: 0.0, params.radius)
            Result.Success(result)
        } ?: Result.Error.BusinessException("User is not set location")
    }
}