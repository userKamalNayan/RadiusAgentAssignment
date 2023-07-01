package com.radiusagent.data.datasource.local

import com.radiusagent.data.db.dao.FacilitiesDao
import com.radiusagent.domain.model.entities.FacilityEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/** @Author: Kamal Nayan
 * @since: 30/06/23 at 8:49 pm */
class FacilityLocalDataSource @Inject constructor(private var facilitiesDao: FacilitiesDao) {

    suspend fun insertFacility(facilityEntity: List<FacilityEntity>) {
        return facilitiesDao.insertFacility(facilityEntity)
    }

    suspend fun getAllFacilities(): List<FacilityEntity> {
        return facilitiesDao.getAllFacilities()
    }

    suspend fun clearFacilityDb() {
        facilitiesDao.clearFacility()
    }
}