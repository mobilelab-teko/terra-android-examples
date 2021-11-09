package com.example.tracker_android.ui.fragment.tabnavigation

import com.example.tracker_android.R
import com.example.tracker_android.databinding.FragmentHomeTabBinding
import com.example.tracker_android.tracking.AppScreenName
import com.example.tracker_android.ui.fragment.BaseTrackableFragment
import vn.teko.android.tracker.event.body.ScreenViewEventBody

class HomeTabFragment : BaseTrackableFragment<FragmentHomeTabBinding>() {

    override fun screenName(): ScreenViewEventBody.ScreenName = AppScreenName.HomeTab

    override val layoutId: Int = R.layout.fragment_home_tab

    override fun initViews() {
        println("HomeTabFragment.initViews")
    }
}