package com.example.tracker_android.ui.fragment.home

import com.example.tracker_android.R
import com.example.tracker_android.databinding.BottomsheetDemoBinding
import com.example.tracker_android.tracking.AppContentType
import com.example.tracker_android.tracking.AppScreenName
import com.example.tracker_android.ui.fragment.BaseTrackableBottomSheet
import vn.teko.android.tracker.event.body.ScreenViewEventBody

class DemoBottomSheet : BaseTrackableBottomSheet<BottomsheetDemoBinding>() {

    override fun screenName(): ScreenViewEventBody.ScreenName = AppScreenName.DemoBottomSheet

    override fun contentType(): ScreenViewEventBody.ContentType = AppContentType.ContentType1

    override val layoutId: Int = R.layout.bottomsheet_demo

    override fun initViews() {

    }

}