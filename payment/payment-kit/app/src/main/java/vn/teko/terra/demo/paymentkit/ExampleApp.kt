package vn.teko.terra.demo.paymentkit

import android.app.Application
import vn.teko.terra.core.android.terra.TerraApp

class ExampleApp : Application() {
    override fun onCreate() {
        TerraApp.initializeApp(this)

        super.onCreate()
    }
}


