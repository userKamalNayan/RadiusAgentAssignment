package com.radiusagent.android.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/** @Author: Kamal Nayan
 * @since: 01/07/23 at 12:37 pm */
abstract class BaseActivity<VDB : ViewDataBinding>(
    @LayoutRes private val contentLayoutId: Int,
) : AppCompatActivity() {
    protected lateinit var binding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<VDB>(this, contentLayoutId)
        binding.lifecycleOwner = this
        initViews()
        setViewModelToBinding()
        setData()
        setListeners()
        setObservers()
    }

    protected abstract fun initViews()

    protected abstract fun setViewModelToBinding()

    protected abstract fun setObservers()

    protected abstract fun setListeners()

    protected abstract fun setData()
}