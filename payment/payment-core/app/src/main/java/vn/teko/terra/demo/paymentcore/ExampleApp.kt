package vn.teko.terra.demo.paymentcore

import android.app.Application
import vn.teko.android.payment.manager.TerraPayment
import vn.teko.android.payment.v2.IPaymentGateway
import vn.teko.terra.core.android.terra.TerraApp

class ExampleApp: Application() {

    override fun onCreate() {
        TerraApp.initializeApp(this)
        super.onCreate()
    }
}