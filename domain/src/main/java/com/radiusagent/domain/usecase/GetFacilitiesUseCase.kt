package com.radiusagent.domain.usecase

import com.radiusagent.domain.repository.FacilityRepository
import javax.inject.Inject

/** @Author: Kamal Nayan
 * @since: 01/07/23 at 12:19 pm */
class GetFacilitiesUseCase @Inject constructor(private val repository: FacilityRepository) {
    suspend fun invoke() = repository.getFacilityAndExclusions()
}