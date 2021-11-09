package com.example.tracker_android.ui.activity

import android.content.Intent
import com.example.tracker_android.BuildConfig
import com.example.tracker_android.R
import com.example.tracker_android.databinding.ActivityMainBinding
import vn.teko.android.core.util.instancesmanager.AppIdentifier
import vn.teko.android.tracker.manager.TerraTracker
import vn.teko.terra.core.android.terra.TerraApp


class MainActivity : BaseActivity<ActivityMainBinding>(), AppIdentifier {

    override val layoutId: Int = R.layout.activity_main

    private val terraApp: TerraApp by lazy {
        TerraApp.getInstance(BuildConfig.APPLICATION_ID)
    }

    private val terraTracker by lazy {
        TerraTracker.getInstance(terraApp)
    }

    override fun initViews() {

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        terraTracker.onNewIntent(this, intent)
    }

    override val appIdentifier: String
        get() = BuildConfig.APPLICATION_ID
    
}