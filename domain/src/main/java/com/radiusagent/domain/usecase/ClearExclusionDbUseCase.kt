package com.radiusagent.domain.usecase

import com.radiusagent.domain.repository.FacilityRepository
import javax.inject.Inject

/** @Author: Kamal Nayan
 * @since: 02/07/23 at 12:17 am */
class ClearExclusionDbUseCase @Inject constructor(private val repository: FacilityRepository) {
    suspend fun invoke() = repository.clearExclusionDb()
}