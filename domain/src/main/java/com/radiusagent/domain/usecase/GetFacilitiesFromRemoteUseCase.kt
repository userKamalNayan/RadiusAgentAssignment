package com.radiusagent.domain.usecase

import com.radiusagent.domain.model.response.FacilityResponse
import com.radiusagent.domain.repository.FacilityRepository
import javax.inject.Inject

/** @Author: Kamal Nayan
 * @since: 01/07/23 at 12:22 pm */
class GetFacilitiesFromRemoteUseCase @Inject constructor(private val repository: FacilityRepository) {
    suspend fun invoke() = repository.getFacilityAndExclusionsFromRemote()
}