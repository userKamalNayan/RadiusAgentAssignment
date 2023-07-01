package com.radiusagent.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.radiusagent.data.db.dao.ExclusionsDao
import com.radiusagent.data.db.dao.FacilitiesDao
import com.radiusagent.domain.converter.FacilityResponseConverter
import com.radiusagent.domain.model.entities.FacilityEntity
import com.radiusagent.domain.model.entities.db.ExclusionDbEntity

/** @Author: Kamal Nayan
 * @since: 30/06/23 at 7:59 pm */

@Database(entities = [FacilityEntity::class, ExclusionDbEntity::class], version = 1)
@TypeConverters(FacilityResponseConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun facilitiesDao(): FacilitiesDao

    abstract fun exclusionsDao(): ExclusionsDao
}