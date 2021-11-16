package vn.teko.android.superapp.utils.databinding

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

object ViewBindingAdapters {

    @JvmStatic
    @BindingAdapter("visible")
    fun setVisible(
        view: View,
        isVisible: Boolean
    ) {
        view.isVisible = isVisible
    }

}