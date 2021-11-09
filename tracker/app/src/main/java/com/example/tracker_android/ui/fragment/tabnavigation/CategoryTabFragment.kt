package com.example.tracker_android.ui.fragment.tabnavigation

import androidx.navigation.fragment.findNavController
import com.example.tracker_android.R
import com.example.tracker_android.databinding.FragmentCategoryTabBinding
import com.example.tracker_android.tracking.AppScreenName
import com.example.tracker_android.ui.fragment.BaseTrackableFragment
import vn.teko.android.tracker.event.body.ScreenViewEventBody

class CategoryTabFragment : BaseTrackableFragment<FragmentCategoryTabBinding>() {

    override fun screenName(): ScreenViewEventBody.ScreenName = AppScreenName.CategoryTab

    override val layoutId: Int = R.layout.fragment_category_tab

    override fun initViews() {
        println("CategoryTabFragment.initViews")

        viewDataBinding?.btnToSubCategory?.setOnClickListener {
            findNavController().navigate(CategoryTabFragmentDirections.actionCategoryFragmentToSubCategoryFragment())
        }
    }
}