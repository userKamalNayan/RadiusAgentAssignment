package com.radiusagent.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.radiusagent.domain.model.entities.ExclusionEntity
import com.radiusagent.domain.model.entities.db.ExclusionDbEntity

/** @Author: Kamal Nayan
 * @since: 30/06/23 at 8:36 pm */
@Dao
interface ExclusionsDao {

    @Query("SELECT * FROM exclusion_table")
    suspend fun getAllExclusions(): List<ExclusionDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExclusion(exclusionDbEntity: List<ExclusionDbEntity>)

    @Query("DELETE FROM exclusion_table")
    suspend fun clearExclusions()
}