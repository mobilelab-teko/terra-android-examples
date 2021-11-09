package com.example.tracker_android

import androidx.multidex.MultiDexApplication
import vn.teko.android.tracker.manager.TerraTracker
import vn.teko.terra.core.android.pandora.model.TerraConfig
import vn.teko.terra.core.android.terra.TerraApp

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        val terraApp = TerraApp.initializeApp(
            application = this,
            appName = BuildConfig.APPLICATION_ID,
            terraConfig = TerraConfig(
                terraClientId = "demosuperapp:android:playstore:0.0.1",
                terraEnvironment = TerraConfig.TerraEnv.Develop
            )
        )

        TerraTracker.getInstance(terraApp)
    }
}