package com.example.tracker_android.ui.fragment.home.model

import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.example.tracker_android.R
import com.example.tracker_android.databinding.ItemTitleBinding

@EpoxyModelClass(layout = R.layout.item_title)
abstract class TitleItemModel : DataBindingEpoxyModel() {

    @EpoxyAttribute
    var title: String = ""

    override fun setDataBindingVariables(binding: ViewDataBinding?) {
        (binding as? ItemTitleBinding)?.apply {
            tvTitle.text = title
        }
    }

}