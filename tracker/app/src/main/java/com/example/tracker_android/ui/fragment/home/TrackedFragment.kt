package com.example.tracker_android.ui.fragment.home

import com.example.tracker_android.R
import com.example.tracker_android.databinding.FragmentTrackedBinding
import com.example.tracker_android.tracking.AppScreenName
import com.example.tracker_android.ui.fragment.BaseTrackableFragment
import vn.teko.android.tracker.event.body.ScreenViewEventBody

class TrackedFragment : BaseTrackableFragment<FragmentTrackedBinding>() {

    override fun shouldTrack(): Boolean = true

    override fun screenName(): ScreenViewEventBody.ScreenName = AppScreenName.Tracked

    override val layoutId: Int = R.layout.fragment_tracked

    override fun initViews() {
        
    }

}