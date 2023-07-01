package com.radiusagent.domain.model.entities.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.radiusagent.domain.model.entities.ExclusionEntity

/** @Author: Kamal Nayan
 * @since: 30/06/23 at 8:25 pm
 *
 * Because option id's are unique so we are just storing the
 * exclusion pairs of option ids.
 * */
@Entity(tableName = "exclusion_table")
data class ExclusionDbEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val exclusionList:List<ExclusionEntity>,
){
    constructor(exclusionList: List<ExclusionEntity>):this(0,exclusionList)
}
