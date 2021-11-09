package com.example.tracker_android.ui.activity

import android.os.Bundle
import com.example.tracker_android.R
import com.example.tracker_android.databinding.ActivityThirdBinding
import com.example.tracker_android.tracking.AppContentType
import com.example.tracker_android.tracking.AppScreenName
import vn.teko.android.tracker.core.TrackableScreen
import vn.teko.android.tracker.event.body.ScreenViewEventBody

class ThirdActivity : BaseActivity<ActivityThirdBinding>(), TrackableScreen {

    private var shouldTrack: Boolean = true

    override fun shouldTrack(): Boolean = shouldTrack

    override fun screenName(): ScreenViewEventBody.ScreenName = AppScreenName.ThirdActivity

    override fun contentType(): ScreenViewEventBody.ContentType = AppContentType.ContentType1

    override val layoutId: Int = R.layout.activity_third

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shouldTrack = intent.getBooleanExtra("shouldTrack", true)

        viewDataBinding?.title?.text = getString(R.string.thirdActivity, shouldTrack.toString())
    }

}