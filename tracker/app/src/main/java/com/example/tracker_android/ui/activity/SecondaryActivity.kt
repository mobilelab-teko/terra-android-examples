package com.example.tracker_android.ui.activity

import android.content.Intent
import com.example.tracker_android.R
import com.example.tracker_android.databinding.ActivitySecondaryBinding
import com.example.tracker_android.tracking.AppContentType
import com.example.tracker_android.tracking.AppScreenName
import vn.teko.android.tracker.core.TrackableScreen
import vn.teko.android.tracker.event.body.ScreenViewEventBody

class SecondaryActivity : BaseActivity<ActivitySecondaryBinding>(), TrackableScreen {

    override fun shouldTrack(): Boolean = true

    override fun screenName(): ScreenViewEventBody.ScreenName = AppScreenName.SecondaryActivity

    override fun contentType(): ScreenViewEventBody.ContentType = AppContentType.ContentType1

    override val layoutId: Int = R.layout.activity_secondary

    override fun initViews() {
        viewDataBinding?.btnGotoThirdActivity?.setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java).apply {
                putExtra(
                    "shouldTrack",
                    true
                )
            })
        }

        viewDataBinding?.btnGotoUntrackedThirdActivity?.setOnClickListener {
            // this will simulate an activity interruption by a third party activity
            // we do not want to re-enter screen in this case
            startActivity(Intent(this, ThirdActivity::class.java).apply {
                putExtra(
                    "shouldTrack",
                    false
                )
            })
        }
    }

}