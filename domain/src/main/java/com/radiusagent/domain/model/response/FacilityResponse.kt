package com.radiusagent.domain.model.response

import com.radiusagent.domain.model.base.BaseResponse
import com.radiusagent.domain.model.entities.ExclusionEntity
import com.radiusagent.domain.model.entities.FacilityEntity

/** @Author: Kamal Nayan
 * @since: 30/06/23 at 7:40 pm */
data class FacilityResponse(
    val facilities: List<FacilityEntity>,
    val exclusions: List<List<ExclusionEntity>>
) : BaseResponse()
