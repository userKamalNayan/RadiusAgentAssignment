package com.radiusagent.android.di.accessors

import com.radiusagent.domain.usecase.GetFacilitiesFromRemoteUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** @Author: Kamal Nayan
 * @since: 02/07/23 at 1:14 am */

@EntryPoint
@InstallIn(SingletonComponent::class)
interface GetFacilitiesFromRemoteAccessor {
    fun getFacilitiesFromRemote():GetFacilitiesFromRemoteUseCase
}