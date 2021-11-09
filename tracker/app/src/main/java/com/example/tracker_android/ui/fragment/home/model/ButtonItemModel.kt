package com.example.tracker_android.ui.fragment.home.model

import android.view.View.OnClickListener
import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash
import com.airbnb.epoxy.EpoxyModelClass
import com.example.tracker_android.R
import com.example.tracker_android.databinding.ItemButtonBinding

@EpoxyModelClass(layout = R.layout.item_button)
abstract class ButtonItemModel : DataBindingEpoxyModel() {

    @EpoxyAttribute
    var title: String? = null

    @EpoxyAttribute(DoNotHash)
    var clickListener: OnClickListener? = null

    override fun setDataBindingVariables(binding: ViewDataBinding?) {
        (binding as? ItemButtonBinding)?.apply {
            btnItem.text = title
            btnItem.setOnClickListener(clickListener)
        }
    }

    override fun unbind(holder: DataBindingHolder) {
        (holder.dataBinding as? ItemButtonBinding)?.apply {
            btnItem.setOnClickListener(null)
        }

        super.unbind(holder)
    }

}