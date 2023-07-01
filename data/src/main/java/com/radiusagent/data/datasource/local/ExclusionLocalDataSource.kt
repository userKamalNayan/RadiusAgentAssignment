package com.radiusagent.data.datasource.local

import com.radiusagent.data.db.dao.ExclusionsDao
import com.radiusagent.domain.model.entities.ExclusionEntity
import com.radiusagent.domain.model.entities.db.ExclusionDbEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/** @Author: Kamal Nayan
 * @since: 30/06/23 at 8:52 pm */
class ExclusionLocalDataSource @Inject constructor(private var exclusionsDao: ExclusionsDao) {

    suspend fun insertExclusion(exclusionEntity: List<ExclusionDbEntity>) {
        withContext(Dispatchers.IO) {
            exclusionsDao.insertExclusion(exclusionEntity)
        }
    }

    suspend fun getAllExclusions(): List<ExclusionDbEntity> {
        return withContext(Dispatchers.IO) {
            exclusionsDao.getAllExclusions()
        }
    }

   suspend fun clearExclusionDb(){
        exclusionsDao.clearExclusions()
    }
}