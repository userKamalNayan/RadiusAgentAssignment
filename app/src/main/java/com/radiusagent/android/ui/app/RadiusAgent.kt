package com.radiusagent.android.ui.app

import android.app.Application
import com.radiusagent.android.BuildConfig
import com.radiusagent.commons.timber.CrashlyticsLogTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/** @Author: Kamal Nayan
 * @since: 01/07/23 at 1:16 pm */
@HiltAndroidApp
class RadiusAgent : Application() {
    override fun onCreate() {
        super.onCreate()
        initDebuggingTools()
    }

    private fun initDebuggingTools() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsLogTree())
        }
    }
}