package vn.com.libertime.database.repository

import org.jetbrains.exposed.sql.transactions.transaction
import vn.com.libertime.database.toUserProfile
import vn.com.libertime.port.um.entity.SearchUserProfile
import vn.com.libertime.port.um.required.UserLocationRepository

public class DefaultUserLocationRepository : UserLocationRepository {
    override suspend fun searchInRadius(centerLat: Double, centerLng: Double, radius: Double): List<SearchUserProfile> =
        transaction {
            ("SELECT id,address,university,location,\"lastLoginDate\"," +
                    "location <-> ST_MakePoint($centerLat,$centerLng)::geography as distance" +
                    " FROM userprofiles WHERE ST_DWithin(location::geography, ST_MakePoint($centerLat,$centerLng)::geography, $radius)" +
                    " ORDER BY distance ASC")
                .execAndMap { rs -> toUserProfile(rs) }
        }
}