package com.radiusagent.android.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.radiusagent.android.base.BaseViewModel
import com.radiusagent.domain.model.network.ErrorResponse
import com.radiusagent.domain.model.network.Failure
import com.radiusagent.domain.model.response.FacilityResponse
import com.radiusagent.domain.usecase.ClearExclusionDbUseCase
import com.radiusagent.domain.usecase.ClearFacilityDbUseCase
import com.radiusagent.domain.usecase.GetFacilitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/** @Author: Kamal Nayan
 * @since: 01/07/23 at 12:36 pm */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFacilitiesUseCase: GetFacilitiesUseCase,
    private val clearExclusionDbUseCase: ClearExclusionDbUseCase,
    private val clearFacilityDbUseCase: ClearFacilityDbUseCase,
) : BaseViewModel() {

    private val _getFacilityResponse = MutableLiveData<FacilityResponse>()
    val getFacilityResponse: LiveData<FacilityResponse>
        get() = _getFacilityResponse

    private val _errorGettingFacility = MutableLiveData<Pair<Failure, ErrorResponse?>>()
    val errorGettingFacility: LiveData<Pair<Failure, ErrorResponse?>>
        get() = _errorGettingFacility


    fun getFacilities() {
        viewModelScope.launch(Dispatchers.IO) {
            setIsLoading(true)
            val response = getFacilitiesUseCase.invoke()
            response.successOrError(::handleGetFacilitiesSuccess, ::handleGetFacilitiesFailure)
        }
    }

    private fun handleGetFacilitiesSuccess(facilityResponse: FacilityResponse) {
        _getFacilityResponse.postValue(facilityResponse)
        setIsLoading(false)
    }

    private fun handleGetFacilitiesFailure(failure: Failure, errorResponse: ErrorResponse?) {
        _errorGettingFacility.postValue(Pair(failure, errorResponse))
        setIsLoading(false)
    }

    fun clearDb() {
        viewModelScope.launch(Dispatchers.IO) {
            clearExclusionDbUseCase.invoke()
            clearFacilityDbUseCase.invoke()
        }
    }
}