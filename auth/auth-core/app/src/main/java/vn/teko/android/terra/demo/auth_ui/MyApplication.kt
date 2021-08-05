package vn.teko.android.terra.demo.auth_ui

import android.app.Application
import vn.teko.terra.core.android.terra.TerraApp

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        TerraApp.initializeApp(this)
    }
}