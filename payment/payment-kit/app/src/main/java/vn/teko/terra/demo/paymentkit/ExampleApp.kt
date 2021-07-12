package vn.teko.terra.demo.paymentkit

import android.app.Application
import vn.teko.android.payment.ui.PaymentUISdk
import vn.teko.android.terra.auth.TerraAuth
import vn.teko.apollo.terra.TerraApollo
import vn.teko.iris.terra.TerraIris
import vn.teko.terra.core.android.terra.TerraApp

class ExampleApp : Application() {
    override fun onCreate() {
        val terraApp = TerraApp.initializeApp(this)
        TerraAuth.getInstance(terraApp)
        TerraApollo.getInstance(terraApp)
        TerraIris.getInstance(terraApp)
        PaymentUISdk.getInstance(terraApp)

        super.onCreate()
    }
}