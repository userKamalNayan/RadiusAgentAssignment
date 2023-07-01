package com.radiusagent.android.base

import android.view.ViewManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

/** @Author: Kamal Nayan
 * @since: 01/07/23 at 12:36 pm */
open class BaseViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading


    protected fun setIsLoading(isLoading: Boolean) {
        _loading.postValue(isLoading)
    }
}
