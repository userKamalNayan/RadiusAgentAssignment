package com.radiusagent.android.di

import android.content.Context
import androidx.room.Room
import com.radiusagent.data.db.AppDatabase
import com.radiusagent.data.db.dao.ExclusionsDao
import com.radiusagent.data.db.dao.FacilitiesDao
import com.radiusagent.domain.converter.FacilityResponseConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** @Author: Kamal Nayan
 * @since: 30/06/23 at 8:39 pm */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideFacilitiesDao(appDatabase: AppDatabase): FacilitiesDao {
        return appDatabase.facilitiesDao()
    }

    @Provides
    fun provideExclusionsDao(appDatabase: AppDatabase): ExclusionsDao {
        return appDatabase.exclusionsDao()
    }


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "radius_agent_database")
            .build()
    }


}