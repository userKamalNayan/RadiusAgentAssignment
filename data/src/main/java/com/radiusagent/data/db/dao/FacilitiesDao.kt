package com.radiusagent.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.radiusagent.domain.model.entities.FacilityEntity

/** @Author: Kamal Nayan
 * @since: 30/06/23 at 8:35 pm */
@Dao
interface FacilitiesDao {

    @Query("SELECT * FROM facility_table")
    fun getAllFacilities(): List<FacilityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFacility(facilityDbEntity: List<FacilityEntity>)

    @Query("DELETE FROM facility_table")
    fun clearFacility()

}