package com.radiusagent.android.ui.main

import androidx.activity.viewModels
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.radiusagent.android.R
import com.radiusagent.android.base.BaseActivity
import com.radiusagent.android.databinding.ActivityMainBinding
import com.radiusagent.android.databinding.LayoutEpoxyRadioItemBinding
import com.radiusagent.android.radioItem
import com.radiusagent.android.titleItem
import com.radiusagent.android.worker.RefreshDataWorker
import com.radiusagent.commons.constant.OptionConstant
import com.radiusagent.commons.extension.Emtpy
import com.radiusagent.domain.model.entities.ExclusionEntity
import com.radiusagent.domain.model.entities.FacilityEntity
import com.radiusagent.domain.model.response.FacilityResponse
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()

    /**
     * ALl facilities fetched are stored in this list
     */
    private val facilitiesList = mutableListOf<FacilityEntity>()

    /**
     * All exclusions
     */
    private val exclusionList = mutableListOf<List<ExclusionEntity>>()

    /**
     * pair of facility_id and option_id selected for the facility
     * [String.Empty] as value means the option is not selected
     */
    private val selectedOptionsMap = mutableMapOf<String, String>()

    /**
     * whenever a item is selected its respective excluded items are
     * added in to this map using [buildExclusions]
     */
    private val excludedOptions = mutableSetOf<String>()

    /**
     * holds all exclusion pairs
     * eg: option_id_1 to option_id_4, so if option_id_1 is selected it means option_id_4 can't
     * be selected
     */
    private val exclusionPairMap = mutableMapOf<String, String>()


    override fun initViews() {
        initEpoxyView()
    }

    override fun setViewModelToBinding() {
        binding.vm = viewModel
    }

    override fun setObservers() {
        with(viewModel) {
            getFacilityResponse.observe(this@MainActivity) { response ->
                response?.let {
                    onFacilityResponseReceived(it)
                }
            }

            errorGettingFacility.observe(this@MainActivity) {
                Timber.d("error while fetching = ${it.second}")
            }
        }
    }

    private fun onFacilityResponseReceived(facilityResponse: FacilityResponse) {
        with(facilityResponse) {
            facilitiesList.addAll(facilities)
            exclusionList.addAll(exclusions)
        }
        buildExclusionPair()
        binding.facilitiesEpoxy.requestModelBuild()
    }


    override fun setListeners() {

    }

    override fun setData() {
        with(viewModel) {
            getFacilities()
        }
        setupRefreshWork()
    }

    private fun setupRefreshWork() {
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "REFRESH_DATA_WORK",
            ExistingPeriodicWorkPolicy.KEEP,
            createWorkRequest()
        )
    }

    private fun createWorkRequest(): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(1, TimeUnit.DAYS)
            .setConstraints(createWorkConstraints())
            .build()
    }


    private fun createWorkConstraints() =
        Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .setRequiresDeviceIdle(false)
            .setRequiresStorageNotLow(false)
            .build()


    private fun initEpoxyView() {
        binding.facilitiesEpoxy.withModels {
            facilitiesList.forEachIndexed { index, facilityEntity ->
                titleItem {
                    id(facilityEntity.name)
                    facilityName(facilityEntity.name)
                }

                facilityEntity.options.forEachIndexed { optionIndex, optionEntity ->
                    radioItem {
                        id(optionEntity.id)
                        isChecked(selectedOptionsMap.containsValue(optionEntity.id))
                        buttonText(optionEntity.name)
                        isEnabled(!excludedOptions.contains(optionEntity.id))
                        optionEntity(optionEntity)
                        onClick { model, _, _, _ ->
                            handleOptionClick(model.optionEntity().id, facilityEntity.facilityId)
                        }
                        onBind { model, view, position ->
                            (view.dataBinding as LayoutEpoxyRadioItemBinding)?.let { itemBinding ->
                                Glide.with(this@MainActivity)
                                    .load(getImageResourceForOption(model.optionEntity().icon))
                                    .into(itemBinding.ivImage)
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * handle option click, refresh ui after updating the selection details
     */
    private fun handleOptionClick(optionId: String, facilityId: String) {
        if (selectedOptionsMap.containsValue(optionId)) {
            selectedOptionsMap.remove(facilityId)
        } else {
            selectedOptionsMap[facilityId] = optionId
        }
        buildExclusions()
        binding.facilitiesEpoxy.requestModelBuild()
    }

    /**
     * when an option is selected then we refresh the [exclusionList] accordingly
     * so that options are shown as enabled and disabled as required
     */
    private fun buildExclusions() {
        excludedOptions.clear()
        selectedOptionsMap.forEach { (_, selectedOptionId) ->
            if (exclusionPairMap.contains(selectedOptionId)) {
                excludedOptions.add(exclusionPairMap[selectedOptionId].orEmpty())

                if (selectedOptionsMap.containsValue(exclusionPairMap[selectedOptionId])) {
                    // in this case a excluded item was previously selected
                    // so we need to remove it from selected map
                    val entry =
                        selectedOptionsMap.entries.find { it.value == exclusionPairMap[selectedOptionId] }
                            ?: return
                    selectedOptionsMap[entry.key] = String.Emtpy
                }
            }
        }
    }

    /**
     * This function maps the options and returns pair
     *
     * Assuming that the list size should be 2 if it is less than that
     * then nothing will be returned
     */
    private fun buildExclusionPair() {
        exclusionPairMap.putAll(exclusionList.map {
            if (it.size < 2)
                return

            it[0].optionsId to it[1].optionsId
        })
    }


    private fun getImageResourceForOption(optionIcon: String): Int {
        return when (optionIcon) {
            OptionConstant.APARTMENT -> R.drawable.apartment
            OptionConstant.CONDO -> R.drawable.condo
            OptionConstant.BOAT -> R.drawable.boat
            OptionConstant.LAND -> R.drawable.land
            OptionConstant.NO_ROOM -> R.drawable.no_room
            OptionConstant.ROOMS -> R.drawable.rooms
            OptionConstant.SWIMMING -> R.drawable.swimming
            OptionConstant.GARDEN -> R.drawable.garden
            OptionConstant.GARAGE -> R.drawable.garage

            else -> {
                android.R.drawable.ic_menu_help
            }
        }
    }
}