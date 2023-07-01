package com.radiusagent.android.bindingadapter

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

/** @Author: Kamal Nayan
 * @since: 01/07/23 at 12:54 pm */

@BindingAdapter("isVisible")
fun setIsVisible(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}

@BindingAdapter("isInvisible")
fun setIsInvisible(view: View, isInvisible: Boolean) {
    view.isInvisible = isInvisible
}