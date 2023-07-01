package com.radiusagent.domain.converter

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.radiusagent.domain.model.entities.ExclusionEntity
import com.radiusagent.domain.model.entities.FacilityEntity
import com.radiusagent.domain.model.entities.OptionEntity
import timber.log.Timber


class FacilityResponseConverter {

    @TypeConverter
    fun fromOptionsEntityToString(value: OptionEntity): String {
        val gson = GsonBuilder().create()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toOptionsEntityFromString(value: String): OptionEntity {
        val gson = GsonBuilder().create()
        val optionJson = gson.toJson(value)
        return gson.fromJson(optionJson, OptionEntity::class.java)
    }

    @TypeConverter
    fun fromOptionListToString(value: List<OptionEntity>): String {
        val gson = GsonBuilder().create()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toOptionListFromString(value: String): List<OptionEntity> {
        val gson = GsonBuilder().create()
        return gson.fromJson(value, Array<OptionEntity>::class.java).toList() ?: emptyList()
    }

    @TypeConverter
    fun fromFacilityListFromString(value: List<FacilityEntity>): String {
        val gson = GsonBuilder().create()
        val exclusionJson = gson.toJson(value)
        return gson.toJson(exclusionJson)
    }

    @TypeConverter
    fun fromExclusionListToString(value: List<ExclusionEntity>): String {
        val gson = GsonBuilder().create()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toExclusionListFromString(value: String): List<ExclusionEntity> {
        val gson = GsonBuilder().create()
        return gson.fromJson(value, Array<ExclusionEntity>::class.java).toList()
            ?: emptyList()
    }
}