package com.example.tracker_android.ui.fragment.tabnavigation

import androidx.navigation.fragment.findNavController
import com.example.tracker_android.R
import com.example.tracker_android.databinding.FragmentSubCategoryBinding
import com.example.tracker_android.tracking.AppScreenName
import com.example.tracker_android.ui.fragment.BaseTrackableFragment
import vn.teko.android.tracker.event.body.ScreenViewEventBody

class SubCategoryFragment : BaseTrackableFragment<FragmentSubCategoryBinding>() {

    override fun screenName(): ScreenViewEventBody.ScreenName = AppScreenName.SubCategory

    override val layoutId: Int = R.layout.fragment_sub_category

    override fun initViews() {
        println("SubCategoryFragment.initViews")

        viewDataBinding?.btnBack?.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}