package com.radiusagent.domain.converter

import com.google.gson.reflect.TypeToken
import com.radiusagent.domain.model.entities.ExclusionEntity
import com.radiusagent.domain.model.entities.OptionEntity
import java.lang.reflect.Type
import java.security.cert.PKIXRevocationChecker.Option

/** @Author: Kamal Nayan
 * @since: 01/07/23 at 3:35 pm */
class OptionsConverter : BaseClassConverter<OptionEntity>(getTypeToken<OptionEntity>())
class OptionListConverter :
    BaseClassConverter<List<OptionEntity>>(getTypeToken<List<OptionEntity>>())

class ExclusionConverter : BaseClassConverter<ExclusionEntity>(getTypeToken<ExclusionEntity>())

class ExclusionListConverter :
    BaseClassConverter<List<ExclusionEntity>>(getTypeToken<List<ExclusionEntity>>())


inline fun <reified T> getTypeToken(): TypeToken<T> {
    return TypeToken.get(T::class.java)
}