package com.radiusagent.domain.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/** @Author: Kamal Nayan
 * @since: 01/07/23 at 3:24 pm */
abstract class BaseClassConverter<T>(val typeToken: TypeToken<T>) {
    private val gson = Gson()

    @TypeConverter
    fun toJson(value: T): String {
        return gson.toJson(value, typeToken.type)
    }

    @TypeConverter
    fun fromJson(json: String): T {
        return gson.fromJson(json, typeToken)
    }
}