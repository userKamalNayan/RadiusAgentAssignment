package com.radiusagent.android.di

import com.radiusagent.data.repositoryimpl.FacilityRepositoryImpl
import com.radiusagent.domain.model.entities.FacilityEntity
import com.radiusagent.domain.repository.FacilityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** @Author: Kamal Nayan
 * @since: 30/06/23 at 7:17 pm */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    internal abstract fun bindFacilityRepository(repository: FacilityRepositoryImpl): FacilityRepository
}