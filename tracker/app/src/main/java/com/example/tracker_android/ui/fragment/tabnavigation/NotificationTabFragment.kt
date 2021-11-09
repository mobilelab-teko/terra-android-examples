package com.example.tracker_android.ui.fragment.tabnavigation

import com.example.tracker_android.R
import com.example.tracker_android.databinding.FragmentNotificationTabBinding
import com.example.tracker_android.tracking.AppScreenName
import com.example.tracker_android.ui.fragment.BaseTrackableFragment
import vn.teko.android.tracker.event.body.ScreenViewEventBody

class NotificationTabFragment : BaseTrackableFragment<FragmentNotificationTabBinding>() {

    override fun screenName(): ScreenViewEventBody.ScreenName = AppScreenName.NotificationTab

    override val layoutId: Int = R.layout.fragment_notification_tab

    override fun initViews() {
        println("NotificationTabFragment.initViews")
    }
}