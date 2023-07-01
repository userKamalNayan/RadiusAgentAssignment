package com.radiusagent.android.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.ForegroundInfo
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.impl.utils.futures.SettableFuture
import com.google.common.util.concurrent.ListenableFuture
import com.radiusagent.android.R
import com.radiusagent.android.di.accessors.ClearExclusionDbUseCaseAccessor
import com.radiusagent.android.di.accessors.ClearFacilityDbUseCaseAccessor
import com.radiusagent.android.di.accessors.GetFacilitiesFromRemoteAccessor
import com.radiusagent.domain.usecase.ClearExclusionDbUseCase
import com.radiusagent.domain.usecase.ClearFacilityDbUseCase
import com.radiusagent.domain.usecase.GetFacilitiesFromRemoteUseCase
import com.radiusagent.domain.usecase.GetFacilitiesUseCase
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/** @Author: Kamal Nayan
 * @since: 02/07/23 at 12:26 am */
class RefreshDataWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())


    private val clearFacilityDbUseCase by lazy {
       getClearFacilityDbInstance()
    }


    private val clearExclusionDbUseCase by lazy {
        getClearExclusionDbInstance()
    }


    private val getFacilitiesFromRemoteUseCase by lazy { getFacilitiesUseCaseInstance() }


    private fun getClearFacilityDbInstance(): ClearFacilityDbUseCase {
        val entryPoint =
            EntryPointAccessors.fromApplication(context, ClearFacilityDbUseCaseAccessor::class.java)
        return entryPoint.clearFacilityDbUseCase()
    }

    private fun getClearExclusionDbInstance(): ClearExclusionDbUseCase {
        val entryPoint =
            EntryPointAccessors.fromApplication(
                context,
                ClearExclusionDbUseCaseAccessor::class.java
            )
        return entryPoint.clearExclusionDbUseCase()
    }

    private fun getFacilitiesUseCaseInstance(): GetFacilitiesFromRemoteUseCase {
        val entryPoint =
            EntryPointAccessors.fromApplication(
                context,
                GetFacilitiesFromRemoteAccessor::class.java
            )
        return entryPoint.getFacilitiesFromRemote()
    }


    override fun doWork(): Result {
        scope.launch {
            clearFacilityDbUseCase.invoke()
            clearExclusionDbUseCase.invoke()
        }
        return runBlocking { getFreshData() }
    }

    private suspend fun getFreshData() = suspendCoroutine<Result> { continuation ->
        scope.launch {
            val response = getFacilitiesFromRemoteUseCase.invoke()
            response.successOrError({
                Timber.d("Refresh data worker : DATA REFRESHED SUCCESSFULLY")
                continuation.resume(Result.success())
            }, { _, _ ->
                Timber.d("Refresh data worker : FAILED TO REFRESH DATA")
                continuation.resume(Result.retry())
            })
        }
    }


    override fun getForegroundInfo(): ForegroundInfo {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ForegroundInfo(1, createNotification(), ServiceInfo.FOREGROUND_SERVICE_TYPE_NONE)
        } else {
            ForegroundInfo(1, createNotification())
        }
    }

    private fun createNotification(): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("silent_notifications", "silent_notifications")
        }
        val notificationBuilderCompat = NotificationCompat.Builder(context, "silent_notifications")
        notificationBuilderCompat.apply {
            setContentTitle("Refreshing Facilities")
            priority = NotificationCompat.PRIORITY_LOW
            setSmallIcon(R.drawable.app_logo)
            setAutoCancel(true)
            setOngoing(true)
            setOnlyAlertOnce(true)
        }
        return notificationBuilderCompat.build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }


    override fun onStopped() {
        super.onStopped()
        Timber.d("Refresh data worker : ON STOPPED")
        scope.cancel()
    }
}