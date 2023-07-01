package com.radiusagent.data.datasource.remote

import com.radiusagent.data.api.FacilityService
import com.radiusagent.data.base.BaseRemoteDataSource
import com.radiusagent.domain.model.network.Failure
import com.radiusagent.domain.model.network.Result
import com.radiusagent.domain.model.response.FacilityResponse
import javax.inject.Inject

/** @Author: Kamal Nayan
 * @since: 30/06/23 at 7:43 pm */
class FacilityRemoteDataSource @Inject constructor(private var facilityService: FacilityService) :
    BaseRemoteDataSource() {

    suspend fun getFacilityAndExclusions(): Result<FacilityResponse, Failure> {
        return getResponse(request = {
            facilityService.getFacilitiesAndExclusions()
        })
    }
}