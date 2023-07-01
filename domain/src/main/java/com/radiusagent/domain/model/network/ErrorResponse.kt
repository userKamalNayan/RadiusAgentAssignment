package com.radiusagent.domain.model.network

import com.radiusagent.domain.model.base.BaseModel
import kotlinx.parcelize.Parcelize

/**
Created by Kamal Nayan on 05/09/22
 **/
@Parcelize data class ErrorResponse(
    var message: String?,
    var exception: Exception?
) : BaseModel()