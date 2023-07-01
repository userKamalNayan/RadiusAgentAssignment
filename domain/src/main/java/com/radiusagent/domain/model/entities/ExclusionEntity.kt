package com.radiusagent.domain.model.entities

import com.google.gson.annotations.SerializedName
import com.radiusagent.domain.model.base.BaseModel
import kotlinx.parcelize.Parcelize

/** @Author: Kamal Nayan
 * @since: 30/06/23 at 7:33 pm */
@Parcelize
data class ExclusionEntity(
    @SerializedName("facility_id") val facilityId: String,
    @SerializedName("options_id") val optionsId: String
) : BaseModel()
