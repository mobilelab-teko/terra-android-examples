package com.example.tracker_android.ui.fragment.home.model

import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.example.tracker_android.R
import com.example.tracker_android.databinding.ItemSingleBinding

@EpoxyModelClass(layout = R.layout.item_single)
abstract class SingleItemModel : DataBindingEpoxyModel() {

    @EpoxyAttribute
    var title: String? = null

    override fun setDataBindingVariables(binding: ViewDataBinding?) {
        (binding as? ItemSingleBinding)?.apply {
            tvTitle.text = title
        }
    }

}