package com.radiusagent.data.api

import com.radiusagent.domain.model.response.FacilityResponse
import retrofit2.Response
import retrofit2.http.GET

/** @Author: Kamal Nayan
 * @since: 30/06/23 at 6:41 pm
 **/
interface FacilityService {

    @GET("iranjith4/ad-assignment/db")
    suspend fun getFacilitiesAndExclusions(): Response<FacilityResponse>

}