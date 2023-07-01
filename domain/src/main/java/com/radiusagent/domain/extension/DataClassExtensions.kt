package com.radiusagent.domain.extension

import com.radiusagent.domain.model.entities.ExclusionEntity
import com.radiusagent.domain.model.entities.db.ExclusionDbEntity

/** @Author: Kamal Nayan
 * @since: 01/07/23 at 4:30 pm */

fun List<ExclusionEntity>.toExclusionDbEntity(): ExclusionDbEntity {
    return ExclusionDbEntity(exclusionList = this)
}