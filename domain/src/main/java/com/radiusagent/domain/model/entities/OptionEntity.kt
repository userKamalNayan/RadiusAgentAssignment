package com.radiusagent.domain.model.entities

import com.radiusagent.domain.model.base.BaseModel
import kotlinx.parcelize.Parcelize

/** @Author: Kamal Nayan
 * @since: 30/06/23 at 7:30 pm */
@Parcelize
data class OptionEntity(
    val name: String,
    val icon: String,
    val id: String
) : BaseModel()
