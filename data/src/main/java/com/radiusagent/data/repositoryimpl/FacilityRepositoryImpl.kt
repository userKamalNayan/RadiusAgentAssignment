package com.radiusagent.data.repositoryimpl

import com.radiusagent.data.datasource.local.ExclusionLocalDataSource
import com.radiusagent.data.datasource.local.FacilityLocalDataSource
import com.radiusagent.data.datasource.remote.FacilityRemoteDataSource
import com.radiusagent.domain.extension.toExclusionDbEntity
import com.radiusagent.domain.model.network.Failure
import com.radiusagent.domain.model.network.Result
import com.radiusagent.domain.model.response.FacilityResponse
import com.radiusagent.domain.repository.FacilityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/** @Author: Kamal Nayan
 * @since: 30/06/23 at 7:20 pm */
class FacilityRepositoryImpl @Inject constructor(
    private val facilityRemoteDataSource: FacilityRemoteDataSource,
    private val facilityLocalDataSource: FacilityLocalDataSource,
    private val exclusionLocalDataSource: ExclusionLocalDataSource
) :
    FacilityRepository {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override suspend fun getFacilityAndExclusions(): Result<FacilityResponse, Failure> {
        val facilities = facilityLocalDataSource.getAllFacilities()
        val exclusions = exclusionLocalDataSource.getAllExclusions()

        return if (facilities.isEmpty() || exclusions.isEmpty()) {
            Timber.d("Facility Repo Impl : fetching data from network")
            getFacilityAndExclusionsFromRemote()
        } else {
            Timber.d("Facility Repo Impl : fetching data from LOCAL DB")
            val exclusionPairList = exclusions.map { it.exclusionList }
            Result.Success(FacilityResponse(facilities, exclusions = exclusionPairList))
        }

    }

    override suspend fun getFacilityAndExclusionsFromRemote(): Result<FacilityResponse, Failure> {
        return withContext(Dispatchers.IO)
        {
            val response = facilityRemoteDataSource.getFacilityAndExclusions()
            suspendCoroutine { continuation ->
                response.successOrError(
                    functionType = {
                        handleGetFacilitySuccess(it) {
                            continuation.resume(response)
                        }
                    },
                    functionFailure = { _, _ ->
                        //do nothing
                    }
                )
            }
        }
    }

    override suspend fun clearExclusionDb() {
        Timber.d("Facility Repo Impl : cleared exclusion db")
        exclusionLocalDataSource.clearExclusionDb()
    }

    override suspend fun clearFacilityDb() {
        Timber.d("Facility Repo Impl : cleared facility db")
        facilityLocalDataSource.clearFacilityDb()
    }

    private fun handleGetFacilitySuccess(facilityResponse: FacilityResponse, onSaved: () -> Unit) {
        scope.launch {
            Timber.d("Facility Repo Impl : will save facilities and exclusions to db")
            withContext(Dispatchers.IO) {
                facilityLocalDataSource.insertFacility(facilityResponse.facilities)
                exclusionLocalDataSource.insertExclusion(facilityResponse.exclusions.map { it.toExclusionDbEntity() })
            }
            Timber.d("Facility Repo Impl : saved facilities and exclusions to db")
            onSaved.invoke()
        }
    }

}