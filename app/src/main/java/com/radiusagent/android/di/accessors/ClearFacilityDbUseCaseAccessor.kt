package com.radiusagent.android.di.accessors

import com.radiusagent.domain.usecase.ClearFacilityDbUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** @Author: Kamal Nayan
 * @since: 02/07/23 at 1:12 am */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface ClearFacilityDbUseCaseAccessor {
    fun clearFacilityDbUseCase():ClearFacilityDbUseCase
}