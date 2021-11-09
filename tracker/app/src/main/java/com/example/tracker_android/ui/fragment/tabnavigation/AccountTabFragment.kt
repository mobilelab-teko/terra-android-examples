package com.example.tracker_android.ui.fragment.tabnavigation

import com.example.tracker_android.R
import com.example.tracker_android.databinding.FragmentAccountTabBinding
import com.example.tracker_android.tracking.AppScreenName
import com.example.tracker_android.ui.fragment.BaseTrackableFragment
import vn.teko.android.tracker.event.body.ScreenViewEventBody

class AccountTabFragment : BaseTrackableFragment<FragmentAccountTabBinding>() {

    override fun screenName(): ScreenViewEventBody.ScreenName = AppScreenName.AccountTab

    override val layoutId: Int = R.layout.fragment_account_tab

    override fun initViews() {
        println("AccountTabFragment.initViews")
    }
}