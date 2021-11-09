package com.example.tracker_android.ui.fragment

import androidx.databinding.ViewDataBinding
import com.example.tracker_android.tracking.AppScreenName
import vn.teko.android.tracker.core.TrackableScreen
import vn.teko.android.tracker.event.body.ScreenViewEventBody

abstract class BaseTrackableBottomSheet<T : ViewDataBinding> : BaseBottomSheet<T>(),
    TrackableScreen {

    override fun shouldTrack(): Boolean = true

    override fun screenName(): ScreenViewEventBody.ScreenName = AppScreenName.Unknown

    override fun contentType(): ScreenViewEventBody.ContentType =
        ScreenViewEventBody.EcommerceContentType.Other

}