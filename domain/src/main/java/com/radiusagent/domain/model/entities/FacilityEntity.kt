package com.radiusagent.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.radiusagent.domain.model.base.BaseModel
import kotlinx.parcelize.Parcelize

/** @Author: Kamal Nayan
 * @since: 30/06/23 at 7:31 pm */
@Parcelize
@Entity(tableName = "facility_table")
data class FacilityEntity(
    @PrimaryKey
    @SerializedName("facility_id") val facilityId: String,
    val name: String,


    val options: List<OptionEntity>,
) : BaseModel()
