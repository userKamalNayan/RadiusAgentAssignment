package com.radiusagent.domain.repository

import com.radiusagent.domain.model.network.Failure
import com.radiusagent.domain.model.network.Result
import com.radiusagent.domain.model.response.FacilityResponse
import kotlinx.coroutines.CoroutineDispatcher

/** @Author: Kamal Nayan
 * @since: 30/06/23 at 7:18 pm */
interface FacilityRepository {

    suspend fun getFacilityAndExclusions(): Result<FacilityResponse, Failure>

    suspend fun getFacilityAndExclusionsFromRemote(): Result<FacilityResponse, Failure>

    suspend fun clearExclusionDb()

    suspend fun clearFacilityDb()
}