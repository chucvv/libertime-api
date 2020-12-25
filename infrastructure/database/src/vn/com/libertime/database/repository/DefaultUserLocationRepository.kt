package vn.com.libertime.database.repository

import org.jetbrains.exposed.sql.transactions.transaction
import vn.com.libertime.database.toUserProfile
import vn.com.libertime.port.um.entity.UserProfile
import vn.com.libertime.port.um.required.UserLocationRepository

public class DefaultUserLocationRepository() : UserLocationRepository {
    override suspend fun searchInRadius(centerLat: Double, centerLng: Double, radius: Double): List<UserProfile> =
        transaction {
            "SELECT * FROM userprofiles uf WHERE ST_DWithin(uf.location::geography, ST_MakePoint($centerLat,$centerLng)::geography, $radius)"
                .execAndMap { rs -> toUserProfile(rs) }
        }
}